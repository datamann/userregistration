package dnb.userregistration.rosterlist

### Read from rosterlist.json
rosterlist := data.username

default allow := false

allow {
    rosterlist[_] == input.user.attributes.preferred_username
}

allow {
    rosterlist[_] == input.user.claims.preferred_username
}