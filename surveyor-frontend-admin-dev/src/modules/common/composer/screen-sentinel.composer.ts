import { HttpLoadScreenBodyResponseMessage } from '@/modules/common/model/http.load-screen-body-message.model';
import appRouter from '@/app/router/app.router';
import Error403Route from '@/app/router/routes/workspace/error-403.route';
import { GrantedAuthority } from '@/modules/common/model/granted-authority.model';
import { useGrantedAuthorityComposer } from '@/modules/common/composer/granted-authority.composer';

export const useScreenSentinelComposer = () => {
    const grantedAuthorityComposer = useGrantedAuthorityComposer();

    /*
    function verifyAccessPermission(grantedAuthority?: GrantedAuthority)
    {
        console.log("### ScreenSentinelComposer.verifyAccessPermission ###");
        if(grantedAuthority && grantedAuthorityComposer.hasAccessActionPermission(grantedAuthority)) {
            // do nothing
        } else {
            appRouter.getRouter().push({ path: Error403Route.getRoutePath() });
        }
    }
    */

    function verifyAccessPermission(loadScreenBodyResponse?: HttpLoadScreenBodyResponseMessage<any>)
    {
        console.log("### ScreenSentinelComposer.verifyAccessPermission ###");

        if(loadScreenBodyResponse && loadScreenBodyResponse.shouldAuthorize) {
            if(!loadScreenBodyResponse.grantedAuthority || !grantedAuthorityComposer.hasAccessActionPermission(loadScreenBodyResponse.grantedAuthority)) {
                appRouter.getRouter().push({ path: Error403Route.getRoutePath() });
            }
        }
    }

    return {
        verifyAccessPermission
    };
};