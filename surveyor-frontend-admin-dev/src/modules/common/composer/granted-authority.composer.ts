import { GrantedAuthority } from '@/modules/common/model/granted-authority.model';

export const useGrantedAuthorityComposer = () => {
    function hasActionPermission(grantedAuthority: GrantedAuthority, action: string): boolean
    {
        if(grantedAuthority && grantedAuthority.privileges && grantedAuthority.privileges.length > 0 && grantedAuthority.privileges[0].programActions) {
            return grantedAuthority.privileges[0].programActions.findIndex(e => e === action) >= 0;
        }
        return false;
    }

    function hasAccessActionPermission(grantedAuthority: GrantedAuthority): boolean
    {
        return hasActionPermission(grantedAuthority, 'Access');
    }

    function hasViewActionPermission(grantedAuthority: GrantedAuthority): boolean
    {
        return hasActionPermission(grantedAuthority, 'View');
    }

    function hasCreateActionPermission(grantedAuthority: GrantedAuthority): boolean
    {
        return hasActionPermission(grantedAuthority, 'Create');
    }

    function hasUpdateActionPermission(grantedAuthority: GrantedAuthority): boolean
    {
        return hasActionPermission(grantedAuthority, 'Update');
    }

    function hasDeleteActionPermission(grantedAuthority: GrantedAuthority): boolean
    {
        return hasActionPermission(grantedAuthority, 'Delete');
    }

    function hasCopyActionPermission(grantedAuthority: GrantedAuthority): boolean
    {
        return hasActionPermission(grantedAuthority, 'Copy');
    }

    function hasImportActionPermission(grantedAuthority: GrantedAuthority): boolean
    {
        return hasActionPermission(grantedAuthority, 'Import');
    }

    function hasExportActionPermission(grantedAuthority: GrantedAuthority): boolean
    {
        return hasActionPermission(grantedAuthority, 'Export');
    }

    return {
        hasActionPermission,
        hasAccessActionPermission,
        hasViewActionPermission,
        hasCreateActionPermission,
        hasUpdateActionPermission,
        hasDeleteActionPermission,
        hasCopyActionPermission,
        hasImportActionPermission,
        hasExportActionPermission
    };
};