import Swal, { SweetAlertResult } from 'sweetalert2';

const COLOR_TURQUOISE = "#1abc9c";

const BUTTON_CONFIRM_COLOR = undefined;
const BUTTON_CANCEL_COLOR = undefined;

const BUTTON_INFO_COLOR = COLOR_TURQUOISE;
const BUTTON_SUCCESS_COLOR = COLOR_TURQUOISE;
const BUTTON_QUESTION_COLOR = COLOR_TURQUOISE;
const BUTTON_WARNING_COLOR = COLOR_TURQUOISE;
const BUTTON_ERROR_COLOR = COLOR_TURQUOISE;

class EzAlert
{
    public static showSuccess(args: AlertArgument): Promise<SweetAlertResult<any>>
    {
        const alert = Swal.fire({
            title: args.title,
            text: args.text,
            icon: IconType.success,
            confirmButtonColor: args.buttonColor ? args.buttonColor : BUTTON_SUCCESS_COLOR,
            confirmButtonText: args.buttonText,
        });

        EzAlert.registerButtonClickedHandler(alert, args);

        return alert;
    }

    public static showInfo(args: AlertArgument): Promise<SweetAlertResult<any>>
    {
        const alert = Swal.fire({
            title: args.title,
            text: args.text,
            icon: IconType.info,
            confirmButtonColor: args.buttonColor ? args.buttonColor : BUTTON_INFO_COLOR,
            confirmButtonText: args.buttonText,
        });

        EzAlert.registerButtonClickedHandler(alert, args);

        return alert;
    }

    public static showConfirm(args: ConfirmAlertArgument): Promise<SweetAlertResult<any>>
    {
        const alert = Swal.fire({
            title: args.title,
            text: args.text,
            icon: args.icon || IconType.question,
            confirmButtonColor: args.buttonColor ? args.buttonColor : BUTTON_QUESTION_COLOR,
            confirmButtonText: args.buttonText,
            showCancelButton: true,
            cancelButtonText: args.cancelButtonText,
            cancelButtonColor: args.cancelButtonColor ? args.cancelButtonColor : BUTTON_CANCEL_COLOR
        });
        
        EzAlert.registerButtonClickedHandler(alert, args);

        return alert;
    }
    
    public static showWarning(args: AlertArgument): Promise<SweetAlertResult<any>>
    {
        const alert = Swal.fire({
            title: args.title,
            text: args.text,
            icon: IconType.warning,
            confirmButtonColor: args.buttonColor ? args.buttonColor : BUTTON_WARNING_COLOR,
            confirmButtonText: args.buttonText,
        });

        EzAlert.registerButtonClickedHandler(alert, args);

        return alert;
    }

    public static showError(args: AlertArgument): Promise<SweetAlertResult<any>>
    {
        const alert = Swal.fire({
            title: args.title,
            text: args.text,
            icon: IconType.error,
            confirmButtonColor: args.buttonColor ? args.buttonColor : BUTTON_ERROR_COLOR,
            confirmButtonText: args.buttonText,

            // showCancelButton: true,
            // cancelButtonColor: '#d33',
            // showClass: {
            //     popup: 'animate__animated animate__fadeInDown',
            // },
            // hideClass: {
            //     popup: 'animate__animated animate__fadeOutUp'
            // }
        });

        EzAlert.registerButtonClickedHandler(alert, args);

        return alert;
    }

    protected static registerButtonClickedHandler(alert: Promise<SweetAlertResult<any>>, args: AlertArgument)
    {
        if(args.buttonClickedHandler) {
            alert.then((result) => {
                if(args.buttonClickedHandler) {
                    args.buttonClickedHandler({
                        isConfirmed: result.isConfirmed,
                        isDenied: result.isDenied,
                        isDismissed: result.isDismissed,
                        value: result.value
                    });
                }
            });
        }
    }
}

export interface AlertArgument {
    title: string;
    text: string;
    buttonText: string;
    buttonColor?: string;
    buttonClickedHandler?: (result: DialogResult) => void
}

export interface ConfirmAlertArgument extends AlertArgument {
    title: string;
    text: string;
    buttonText: string;
    buttonColor?: string;
    showCancelButton?: boolean;
    cancelButtonText: string;
    cancelButtonColor?: string;
    icon?:any;
}

export enum IconType {
    success = 'success',
    info = 'info',
    question = 'question',
    warning = 'warning',
    error = "error"
}

export interface DialogResult {
    isConfirmed: boolean;
    isDenied: boolean;
    isDismissed: boolean;
    value: boolean
}

export default EzAlert;