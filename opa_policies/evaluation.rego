package dnb.userregistration.auth.eval

#import rego.v1
import future.keywords.if
import future.keywords.in

import data.readOnlyList
import data.denyHrCodes
import data.rosterList

default not_denied := true
default allow_write := false

allow_write := true if {
    user_write_permission
    not user_converted_to_read_only
}

allow_write := false if {
    user_read_only_permission
}

allow_write := false if {
    user_converted_to_read_only
}

not_denied := false if {
    denied_access_from_hrcode
}

not_denied := false if {
    denied_access_not_rosterlist
}

# Access is denied if user has denied code as HR Code.
denied_access_from_hrcode if {
    hrData := input.hr_data[_]
    denyHrCodes[_] == hrData
}

# Access is denied if user is not on rosterlist.
denied_access_not_rosterlist if {
    not input.preferred_username in rosterList
}

# Users permission is changed to "read only" if on "read only" list.
user_converted_to_read_only if {
    input.preferred_username in readOnlyList
}

# User with "read only" permission.
user_read_only_permission if {
    input.method == "GET"
	p := "dnb.userregistration.read"
    p in input.authorizations
}

# User with "write" permission.
user_write_permission if {
    input.method in {"GET", "POST", "PUT", "DELETE"}
	p := "dnb.userregistration.write"
	p in input.authorizations
}