package ru.ulmc.communityFeedback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ulmc.communityFeedback.controller.model.OptionUI;
import ru.ulmc.communityFeedback.controller.model.TopicUI;
import ru.ulmc.communityFeedback.dao.entity.Option;
import ru.ulmc.communityFeedback.dao.entity.Topic;
import ru.ulmc.communityFeedback.dao.entity.User;
import ru.ulmc.communityFeedback.service.CommunityService;
import ru.ulmc.communityFeedback.service.api.OptionDTO;
import ru.ulmc.communityFeedback.service.api.TopicDTO;
import ru.ulmc.communityFeedback.system.bean.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@Scope(scopeName = "request")
public class IndexController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    UserSession userSession;

    @RequestMapping(path = "/createCandidate", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_VOTE_ADMIN')")
    public ModelAndView createCandidate(@RequestParam("nomination") Long topicID,
                                        @RequestParam("about") String about,
                                        @RequestParam("displayedName") String displayedName,
                                        ModelMap model) {
        OptionDTO optionDTO = new OptionDTO(displayedName, about);
        communityService.createOption(topicID, optionDTO);
        model.put("adminMessage", "Запись создана успешно!");

        return new ModelAndView("index", model);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView index(Principal principal, ModelMap model) {
        List<TopicDTO> data = communityService.getTopics(0);
        if (data == null || data.isEmpty()) {
            // model.put("emptyMessage", "Кандидатов нет.");
            return new ModelAndView("index", model);
        }
        canUserVote(model);

        List<TopicUI> dataList = new ArrayList<>();
        for (TopicDTO n : data) {
            List<OptionUI> list = new ArrayList<>();
            TopicUI nUI = new TopicUI(n.getName(), n.getId(), list);
            dataList.add(nUI);
        }
        model.put("nominations", dataList);
        return new ModelAndView("index", model);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logoutPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ModelAndView("redirect:/login");
    }


    private boolean canUserVote(ModelMap model) {
        boolean can = communityService.canUserVote(userSession);
        model.put("canVote", can);
        return can;
    }
/*
    @RequestMapping(path = "/getResults", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_VOTE_ADMIN')")
    public ModelAndView getResults(Principal principal, ModelMap model) {

        Map<Topic, List<Option>> data = votingDAO.getNominationsWithCandidatesAndVotes();
        if (data == null || data.isEmpty()) {
            return new ModelAndView("index", model);
        }
        canUserVote(principal.getName(), model);

        List<TopicUI> dataList = new ArrayList<>();
        for (Topic n : data.keySet()) {
            List<OptionUI> list = new ArrayList<>();
            for (Option can : data.get(n)) {
                OptionUI candidate = new OptionUI(
                        can.getId(), can.getDisplayedName(),
                        can.getAbout(), can.getVoteCount());
                candidate.setCountFinished(can.getVoteCountFinished());

                list.add(candidate);
            }
            TopicUI nUI = new TopicUI(n.getName(), n.getId(), list);
            dataList.add(nUI);
        }

        List<User> votedUsers = votingDAO.getUsersVotingStatus();

        model.put("results", dataList);
        model.put("nominations", dataList);
        model.put("votedUsers", votedUsers);

        return new ModelAndView("index", model);
    }

    @RequestMapping(path = "/delete/{optionID}/{topicID}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_VOTE_ADMIN')")
    public ModelAndView delete(@PathVariable(value = "optionID") Long optionID,
                               @PathVariable(value = "topicID") Long topicID,
                               Principal principal, ModelMap model) {
        votingDAO.deleteCandidate(topicID, optionID);
        model.put("voteMessage", "Кандидат удален!");
        return index(principal, model);
    }

    @RequestMapping(path = "/vote/{optionID}/{topicID}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView vote(@PathVariable(value = "optionID") Long optionID,
                             @PathVariable(value = "topicID") Long topicID,
                             Principal principal, ModelMap model) {
        if (!canUserVote(principal.getName(), model)) {
            model.put("voteMessage", "Вы уже завершили свое голосование!");
            return index(principal, model);
        }
        votingDAO.vote(principal.getName(), topicID, optionID);
        model.put("voteMessage", "Выбор принят!");

        return index(principal, model);
    }

    @RequestMapping(path = "/cancel/{optionID}/{topicID}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView cancel(@PathVariable(value = "optionID") Long optionID,
                               @PathVariable(value = "topicID") Long topicID,
                               Principal principal, ModelMap model) {
        if (!canUserVote(principal.getName(), model)) {
            model.put("voteMessage", "Вы уже завершили свое голосование!");
            return index(principal, model);
        }
        votingDAO.cancelVote(principal.getName(), topicID, optionID);
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
*/
}