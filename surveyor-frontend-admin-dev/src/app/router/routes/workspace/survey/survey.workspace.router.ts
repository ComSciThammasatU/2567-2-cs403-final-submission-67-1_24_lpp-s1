import { RouteRecordRaw } from 'vue-router';
import FormTemplateSetupRoute from './form-template-setup.route';
import FormDocumentRoute from './form-document.route';

class SurveyWorkspaceRouter
{
    public buildRoutes(): RouteRecordRaw[]
    {
        return [
            FormTemplateSetupRoute.build(),
            FormDocumentRoute.build()
        ];
    }
}

export default new SurveyWorkspaceRouter();