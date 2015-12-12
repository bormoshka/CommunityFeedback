package ru.ulmc.communityFeedback.service.api;

public class OptionDTO extends BaseDTO<Long> {
    protected String about;
    protected String displayedName;

    public OptionDTO() {
    }

    public OptionDTO(String displayedName, String about) {
        this.about = about;
        this.displayedName = displayedName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }
}
