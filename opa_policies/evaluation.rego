package dnb.userregistration.eval

#import rego.v1
import future.keywords.if
import future.keywords.in

import data.readOnlyList
import data.denyHrCodes
import data.rosterList

default not_denied := true
default allow := true

allow {
    user_write_permission
    not user_converted_to_read_only
}

allow {
    user_read_permission
}

not_denied := false {
    denied_access_from_hrcode
}

not_denied := false {
    denied_access_not_rosterlist
}

# Access is denied if user has denied code as HR Code.
denied_access_from_hrcode {
    hrData := input.user.attributes.hr_data[_]
    denyHrCodes[_] == hrData
}

# Access is denied if user is not on rosterlist.
denied_access_not_rosterlist {
    not input.user.attributes.preferred_username in rosterList
}

# Users permission is changed to "read only" if on "read only" list.
user_converted_to_read_only {
    input.user.attributes.preferred_username in readOnlyList
}

# User with "read only" permission.
user_read_permission {
    input.method == "GET"
	r := "dnb.userregistration.read"
    input.user.attributes.authorizations[_] = r
}

# User with "write" permission.
user_write_permission {
    input.method in {"GET", "POST", "PUT", "DELETE"}
	w := "dnb.userregistration.write"
    input.user.attributes.authorizations[_] = w
}