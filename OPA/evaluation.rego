package actions.eval

import future.keywords.in

allowActions[action] {
	some action in evalActions
}

# Read Access
evalActions["read"] {
    input.method == "GET"
	t := "dnb.userregistration.read"
    t in input.authorizations
	type := substring(t, 21, 4)
    type == "read"
}

# Write access
evalActions["write"] {
    input.method in {"GET", "POST", "PUT", "DELETE"}
	t := "dnb.userregistration.write"
	t in input.authorizations
    type := substring(t, 21, 5)
	type == "write"
}

# HR Override
evalActions["deny"] {
    hrData := input.hr_data[_]
    hrOverRide := data.hrCodes[_] == hrData
}

# Rosterlist
evalActions["rosterlist"] {
    data.rosterList[_] == input.preferred_username
}

# Convert write access to read access
evalActions["convertWriteToRead"] {
    data.convertWriteToRead[_] == "true"
}