package ru.ulmc.poll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ulmc.poll.controller.model.CandidateUI;
import ru.ulmc.poll.controller.model.NominationUI;
import ru.ulmc.poll.dao.IVotingDAO;
import ru.ulmc.poll.dao.entity.Candidate;
import ru.ulmc.poll.dao.entity.Nomination;
import ru.ulmc.poll.dao.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {

    @Qualifier("votingDAO")
    @Autowired
    private IVotingDAO votingDAO;

    @RequestMapping(path = "/createCandidate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_VOTE_ADMIN')")
    public ModelAndView createCandidate(@RequestParam("nomination") Long nominationID,
                                       /* @RequestParam("username") String username,*/
                                        @RequestParam("about") String about,
                                        @RequestParam("displayedName") String displayedName,
                                        ModelMap model) {

        votingDAO.createCandidate(/*username*/ null, displayedName, about, nominationID);
        model.put("adminMessage", "Запись создана успешно!");

        return new ModelAndView("index", model);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView index(Principal principal, ModelMap model) {

        Map<Long, Long> votes = votingDAO.findVotes(principal.getName());
        Map<Nomination, List<Candidate>> data = votingDAO.getNominationsWithCandidates();
        if (data == null || data.isEmpty()) {
            // model.put("emptyMessage", "Кандидатов нет.");
            return new ModelAndView("index", model);
        }
        canUserVote(principal.getName(), model);

        List<NominationUI> dataList = new ArrayList<>();
        for (Nomination n : data.keySet()) {
            List<CandidateUI> list = new ArrayList<>();
            for (Candidate can : data.get(n)) {
                CandidateUI candidate = new CandidateUI(can.getId(), can.getDisplayedName(), can.getAbout());
                if (votes != null && !votes.isEmpty()) {
                    if (votes.get(n.getId()) != null && candidate.getId().equals(votes.get(n.getId()))) {
                        candidate.setIsChosen(true);
                    }
                }
                list.add(candidate);
            }
            NominationUI nUI = new NominationUI(n.getName(), n.getId(), list);
            dataList.add(nUI);
        }
        model.put("nominations", dataList);
        return new ModelAndView("index", model);
    }

    @RequestMapping(path = "/getResults", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_VOTE_ADMIN')")
    public ModelAndView getResults(Principal principal, ModelMap model) {

        Map<Nomination, List<Candidate>> data = votingDAO.getNominationsWithCandidatesAndVotes();
        if (data == null || data.isEmpty()) {
            // model.put("emptyMessage", "Кандидатов нет.");
            return new ModelAndView("index", model);
        }
        canUserVote(principal.getName(), model);

        List<NominationUI> dataList = new ArrayList<>();
        for (Nomination n : data.keySet()) {
            List<CandidateUI> list = new ArrayList<>();
            for (Candidate can : data.get(n)) {
                CandidateUI candidate = new CandidateUI(
                        can.getId(), can.getDisplayedName(),
                        can.getAbout(), can.getVoteCount());
                candidate.setCountFinished(can.getVoteCountFinished());

                list.add(candidate);
            }
            NominationUI nUI = new NominationUI(n.getName(), n.getId(), list);
            dataList.add(nUI);
        }

        List<User> votedUsers = votingDAO.getUsersVotingStatus();

        model.put("results", dataList);
        model.put("nominations", dataList);
        model.put("votedUsers", votedUsers);

        return new ModelAndView("index", model);
    }

    @RequestMapping(path = "/delete/{candidateID}/{nominationID}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_VOTE_ADMIN')")
    public ModelAndView delete(@PathVariable(value = "candidateID") Long candidateID,
                               @PathVariable(value = "nominationID") Long nominationID,
                               Principal principal, ModelMap model) {
        votingDAO.deleteCandidate(nominationID, candidateID);
        model.put("voteMessage", "Кандидат удален!");
        return index(principal, model);
    }

    @RequestMapping(path = "/vote/{candidateID}/{nominationID}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView vote(@PathVariable(value = "candidateID") Long candidateID,
                             @PathVariable(value = "nominationID") Long nominationID,
                             Principal principal, ModelMap model) {
        if (!canUserVote(principal.getName(), model)) {
            model.put("voteMessage", "Вы уже завершили свое голосование!");
            return index(principal, model);
        }
        votingDAO.vote(principal.getName(), nominationID, candidateID);
        model.put("voteMessage", "Выбор принят!");

        return index(principal, model);
    }

    @RequestMapping(path = "/cancel/{candidateID}/{nominationID}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView cancel(@PathVariable(value = "candidateID") Long candidateID,
                               @PathVariable(value = "nominationID") Long nominationID,
                               Principal principal, ModelMap model) {
        if (!canUserVote(principal.getName(), model)) {
            model.put("voteMessage", "Вы уже завершили свое голосование!");
            return index(principal, model);
        }
        votingDAO.cancelVote(principal.getName(), nominationID, candidateID);
        model.put("voteMessage", "Выбор отменен!");
        return index(principal, model);
    }

    @RequestMapping(path = "/finish", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView finishVote(Principal principal, ModelMap model) {
        votingDAO.changeUserVoteStatus(principal.getName(), true);
        model.put("voteMessage", "Вы завершили голосование!");
        return index(principal, model);
    }

    @RequestMapping(path = "/IWantVoteAgain", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView reVote(Principal principal, ModelMap model) {
        votingDAO.changeUserVoteStatus(principal.getName(), false);
        model.put("voteMessage", "Теперь Вы можете изменить свой выбор");
        return index(principal, model);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logoutPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ModelAndView("redirect:/login");
    }

    private boolean canUserVote(String username, ModelMap model) {
        boolean can = !votingDAO.isUserFinishedVoting(username);
        model.put("canVote", can);
        return can;
    }
}