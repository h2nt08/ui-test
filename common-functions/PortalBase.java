package org.exoplatform.selenium.platform.portal;

import org.exoplatform.selenium.platform.PlatformBase;

public class PortalBase extends PlatformBase{
	
	public static final String ELEMENT_USER_PROFILE_TAB = "//div[text()='User Profile' and @class='MiddleTab']";
	public static final String ELEMENT_SELECT_USER_LANGUAGE = "//select[@name='user.language']";
	public static final String ELEMENT_SEARCH_ICON_REGISTER = "//img[@class='SearchIcon']";
	public static final String ELEMENT_INPUT_USER_NAME_GIVEN = "//input[@id='user.name.given']";
	public static final String ELEMENT_ACCOUNT_SETTING_TAB = "//div[text()='Account Settings' and @class='MiddleTab']";

	public static final String ELEMENT_USER_DELETE_ICON ="//div[@id='UIListUsersGird']//div[text()='${username}']/../..//img[@class='DeleteUserIcon']";
	public static final String ELEMENT_SEARCH_ICON_USERS_MANAGEMENT = "//form[@id='UISearchForm']/div[2]/a";
	public static final String ELEMENT_LINK_USERS_MANAGEMENT="//a[contains(text(),'Group and Roles')]";
    public static final String ELEMENT_INPUT_SEARCH_USER_NAME = "//input[@name='searchTerm']"; 
    public static final String ELEMENT_EDIT_USER_INFO =  "//img[@title='Edit User Info']" ;      
    public static final String ELEMENT_SELECT_SEARCH_OPTION = "//select[@name='searchOption']";
    public static final String ELEMENT_USER_EDIT_ICON = "//div[@id='UIListUsersGird']/table//tr/td/div[text()='${username}']/../../td[5]//img[@class='ViewUserInfoIcon']";
 
    public static final String ELEMENT_GROUP_ADD_NEW_ICON = "//div[@id='UIOrganizationPortlet']//div[@class='TitleBar']/a[@class='TreeActionIcon AddGroupIcon']";
    public static final String ELEMENT_INPUT_GROUP_NAME = "//input[@name='groupName']";
    public static final String ELEMENT_INPUT_LABEL = "//input[@id='label']";
    public static final String ELEMENT_TEXTAREA_DESCRIPTION = "//textarea[@id='description']";
    public static final String ELEMENT_GROUP_REMOVE_ICON = "//div[@id='UIOrganizationPortlet']//div[@class='TitleBar']/a[@class='TreeActionIcon RemoveGroupIcon']";
    public static final String ELEMENT_GROUP_EDIT_ICON = "//div[@id='UIOrganizationPortlet']//div[@class='TitleBar']/a[@class='TreeActionIcon EditGroupIcon']";
    public static final String ELEMENT_TAB_GROUP_MANAGEMENT = "//div[@class='GroupManagementIcon']/..";
    public static final String ELEMENT_GROUP_TO_SELECT_LINK = "//a[contains(@class, 'NodeIcon') and @title='${group}']";
    public static final String ELEMENT_GROUP_SELECTED = "//a[@class='NodeIcon PortalIcon NodeSelected' and @title='${group}']";
    public static final String ELEMENT_GROUP_SEARCH_USER_ICON = "//form[@id='UIGroupMembershipForm']/div[2]/div/table/tbody/tr[1]/td[2]/a";
    public static final String ELEMENT_GROUP_SEARCH_POPUP_ADD_ICON = "//form[@id='UIUserSelector']//div[@class='UIAction']//a[@class='ActionButton LightBlueStyle']";
    public static final String ELEMENT_SELECT_MEMBERSHIP = "//select[@name='membership']";
    public static final String ELEMENT_GROUP_USER_IN_TABLE = "//div[@class='UIUserInGroup']//div[@title='${username}']";
    
    
    
}
