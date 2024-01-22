package actions.eval

#import rego.v1
import future.keywords.if
import future.keywords.in

import data.readOnlyList
import data.denyHrCodes
import data.rosterList

default not_denied := true
default allow_write := true

allow_write := false if {
    user_converted_to_read_only
}

not_denied := false if {
    denied_access_from_hrcode
}

not_denied := false if {
    denied_access_not_rosterlist
}

# Access is denied if token is not valid.
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

user_read_only_permission if {
    input.method == "GET"
	t := "dnb.userregistration.read"
    t in input.authorizations
}