package org.exoplatform.selenium.platform.portal.func.account;


import org.exoplatform.selenium.platform.portal.PortalBase;
import org.testng.Assert;

/*import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;*/

public class AccountHelpers extends PortalBase {

	public static void addNewUserAccount(String username, String password, String confirmPassword, String firstName, 
            String lastName, String email, String userNameGiven, String language, boolean verify) {
        
		System.out.println("--Create new user using \"New Staff\" portlet--");
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		type(ELEMENT_INPUT_CONFIRM_PASSWORD, confirmPassword, true);
		type(ELEMENT_INPUT_FIRSTNAME, firstName, true);
		type(ELEMENT_INPUT_LASTNAME, lastName, true);
		type(ELEMENT_INPUT_EMAIL, email, true);
		click(ELEMENT_USER_PROFILE_TAB);
		waitForTextPresent("Given Name:");
		type(ELEMENT_INPUT_USER_NAME_GIVEN, userNameGiven, true);
		select(ELEMENT_SELECT_USER_LANGUAGE, language);
		click(ELEMENT_ACCOUNT_SETTING_TAB);
		save();
	
		if (verify) {
			waitForMessage("You have registered a new account.");
			closeMessageDialog();
		}
		
		
	}
	
	public static void goToUsersAndGroupsManagement() {
	        System.out.println("--Go to Users and groups management--");
	        goToPage(ELEMENT_LINK_SetUp, ELEMENT_LINK_SetUp, ELEMENT_LINK_Users, ELEMENT_LINK_USERS_MANAGEMENT);
	    }
	
    public static void deleteUser(String username) {
			String userDeleteIcon = ELEMENT_USER_DELETE_ICON.replace("${username}", username);
			
			System.out.println("--Deleting user " + username + "--");
			if (isTextPresent("Total pages")) {
	            usePaginator(userDeleteIcon, "User " + username + "not found in group");
			}
	        pause(500);
			click(userDeleteIcon);
			waitForConfirmation("Are you sure to delete user " + username + "?");
			waitForTextNotPresent(username);
	        click(ELEMENT_SEARCH_ICON_USERS_MANAGEMENT);
		}
    
	public static void searchUser(String user, String searchOption){
		System.out.println("--Search user " + user + "--");
		if (isTextPresent("Search")){
			type(ELEMENT_INPUT_SEARCH_USER_NAME, user, true);
			select(ELEMENT_SELECT_SEARCH_OPTION, searchOption);
		}	
		pause(3000);
		click(ELEMENT_SEARCH_ICON_USERS_MANAGEMENT);
		waitForTextPresent(user);
	}

	public static void editUser(String username) {
		String userEditIcon = ELEMENT_USER_EDIT_ICON.replace("${username}", username);
		
		System.out.println("--Editing user " + username + "--");
        pause(300);
		click(userEditIcon);
	}

	public static void chooseGroupTab() {
		System.out.println("-- Choose Group Management tab--");
        pause(500);
		click(ELEMENT_TAB_GROUP_MANAGEMENT);
		waitForTextPresent("Group Info");
	}
	
	public static void addGroup(String groupName, String groupLabel, String groupDesc, boolean verify){
		System.out.println("--Add a new group--");
        pause(500);
		click(ELEMENT_GROUP_ADD_NEW_ICON);
		type(ELEMENT_INPUT_GROUP_NAME, groupName, true);
		type(ELEMENT_INPUT_LABEL, groupLabel, true);
		type(ELEMENT_TEXTAREA_DESCRIPTION, groupDesc, true);
		save();
		if (verify) {
            waitForAndGetElement("//a[@title='" + (groupLabel.length() > 0 ? groupLabel : groupName) + "']");
        }
		
	}
	
	public static void selectGroup(String groupXpath) {
		System.out.println("--Select category (" + groupXpath + ")--");
		String groupID = "//a[@title='"+ groupXpath +"']"; 
		waitForAndGetElement("//a[@title='"+ groupXpath +"']");
		click(groupID);
	}
	
	public static void editGroup(String groupName, boolean verify){
		System.out.println("-- Edit group: " + groupName + "--");
		click(ELEMENT_GROUP_EDIT_ICON);
		pause(3000);
	}
	
	public static void deleteGroup(String groupName, boolean verify) {
		System.out.println("-- Delete group: " + groupName + "--");
		click(ELEMENT_GROUP_REMOVE_ICON);
		pause(3000);
		waitForConfirmation("Are you sure to delete this group?");
		if (verify) {
            waitForElementNotPresent(groupName);
        }
		pause(3000);
	}
	
	public static void addUsersToGroup(String userNames, String memberShip, boolean select, boolean verify) {

		System.out.println("--Adding users to group--");
		String[] users = userNames.split(",");
		if (select) {
			click(ELEMENT_GROUP_SEARCH_USER_ICON);
			waitForTextPresent("Select User");
			for (String user : users) {
				check("//input[@name='" + user + "']");
			}
			click(ELEMENT_GROUP_SEARCH_POPUP_ADD_ICON);
            pause(500);
            Assert.assertEquals(getValue(ELEMENT_INPUT_USERNAME), userNames);
		} else {
			type(ELEMENT_INPUT_USERNAME, userNames, true);
		}
		select(ELEMENT_SELECT_MEMBERSHIP, memberShip);
		save();
		if (verify) {
			for (String user : users) {
				String addedUser = ELEMENT_GROUP_USER_IN_TABLE.replace("${username}", user);
				if (isTextPresent("Total pages")) {
                    usePaginator(addedUser, "User " + user + "not found in group");
				} else {
					waitForAndGetElement(addedUser);
				}
			}
		}
	}






}
