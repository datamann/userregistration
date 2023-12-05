package actions

import future.keywords.in

allowActions[action] {
	some action in evalActions
}

evalActions["read"] {
	t := "dnb.userregistration.read"
    t in input.authorizations
	type := substring(t, 21, 4)
    type == "read"
}

evalActions["write"] {
	t := "dnb.userregistration.write"
	t in input.authorizations
    type := substring(t, 21, 5)
	type == "write"
}

evalActions["deny"] {
	
    #hrdata := input.hr_data[_] == "long-term sickness"
    #hrdata := input.hr_data[_] == "parental leave"
    #hrdata := input.hr_data[_] == ""

    #some hrdata in input.hr_data[_]
    val := input.hr_data[_]
    
    print("hrdata: ", val)

    data.denyCodes[_] == val

    val1 := data.denyCodes[_]
    print("denyCodes: ", val1)

    #user_deny_hrdata := data.denyCodes[_]
    
    #print("user_deny_hrdata: ", user_deny_hrdata)

    #count(user_deny_hrdata) > 0


    
    #print("HRData: ", hrdata)

    #data.allowed[_] == input.hr_data[_]
    #data.allowed == hrdata

    #z := data.allowed & input.hr_data[_]
    #print("Z: ", z)

    #user_hrdata := { hrdata | hrdata := input.hr_data[_] }
    

    #user_deny_hrdata := user_hrdata & denyCodes
    #data.allowed[_] == user_hrdata
    #count(user_deny_hrdata) > 0



    # Works - Don´t change
    #  user_hrdata := { hrdata | hrdata := input.hr_data[_] }
    #  user_deny_hrdata := user_hrdata & denyCodes
    #  count(user_deny_hrdata) > 0
}

#denyCodes := { "long-term sickness", "parental leave" }