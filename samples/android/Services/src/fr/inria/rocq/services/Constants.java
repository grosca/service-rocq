package fr.inria.rocq.services;

import fr.inria.arles.yarta.resources.Group;

public class Constants {

	// TODO: change it to your own group id
	private static final int GroupId = 35952;

	public static final String getGroupId() {
		return Group.typeURI + "_" + GroupId;
	}
}
