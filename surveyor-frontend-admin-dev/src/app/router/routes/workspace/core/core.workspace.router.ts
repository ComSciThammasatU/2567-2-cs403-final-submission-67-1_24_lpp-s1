import { RouteRecordRaw } from 'vue-router';
import StaffSetupRoute from './staff-setup.route';
import ProfessorSetupRoute from './professor-setup.route';
import StudentSetupRoute from './student-setup.route';

class CoreWorkspaceRouter
{
    public buildRoutes(): RouteRecordRaw[]
    {
        return [
            StaffSetupRoute.build(),
            ProfessorSetupRoute.build(),
            StudentSetupRoute.build()
        ];
    }
}

export default new CoreWorkspaceRouter();