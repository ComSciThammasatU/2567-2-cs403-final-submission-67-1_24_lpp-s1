import { Role } from "./role.model";
import { Student } from "./student.model";
import { UserGroup } from "./user-group.model";
import { User } from "./user.model";

export interface StudentInfo {
    student: Student;
    user: User;
    roles: Role[];
    userGroups: UserGroup[];
}