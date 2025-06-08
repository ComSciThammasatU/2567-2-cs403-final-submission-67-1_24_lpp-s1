import { Role } from "./role.model";
import { UserGroup } from "./user-group.model";
import { User } from "./user.model";

export interface StaffInfo {
    user: User;
    roles: Role[];
    userGroups: UserGroup[];
}