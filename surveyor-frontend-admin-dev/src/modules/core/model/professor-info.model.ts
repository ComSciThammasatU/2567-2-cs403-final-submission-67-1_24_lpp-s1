import { Role } from "./role.model";
import { UserGroup } from "./user-group.model";
import { User } from "./user.model";

export interface ProfessorInfo {
    user: User;
    roles: Role[];
    userGroups: UserGroup[];
}