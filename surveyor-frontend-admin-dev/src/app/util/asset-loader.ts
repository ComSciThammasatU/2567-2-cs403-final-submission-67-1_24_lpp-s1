import appLogoImgURL from '@/assets/img/logo/app_logo.png';
import appTUSurveyorLogoImgURL from '@/assets/img/logo/TU_Surveyor_Logo.png'
import appLogoTitleImgURL from '@/assets/img/logo/app_logo_title.png';
import appLogo3xImgURL from '@/assets/img/logo/app_logo_3x.png';

class AssetLoader
{
    public static getAppLogoImageURL()
    {
        return appLogoImgURL;
    }

    public static getAppTUSurveyorLogoImgURL()
    {
        return appTUSurveyorLogoImgURL;
    }

    public static getAppLogoTitleImageURL()
    {
        return appLogoTitleImgURL;
    }

    public static getAppLogo3xImageURL()
    {
        return appLogo3xImgURL;
    }
}

export default AssetLoader;