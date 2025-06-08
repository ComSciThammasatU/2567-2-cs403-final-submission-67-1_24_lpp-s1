import { Role } from "./role.model";
import { UserGroup } from "./user-group.model";
import { UserType } from "./user-type.model";
import { User } from "./user.model";

export interface UserProfile {
    user: User;
    userType: UserType;
    userGroups: UserGroup[];
    roles: Role[];
};