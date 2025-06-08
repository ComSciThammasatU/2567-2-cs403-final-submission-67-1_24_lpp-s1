import { App as Application } from 'vue';

import Input from '@/modules/common/ui/widgets/common/form/input/Input.vue';
import TextInput from '@/modules/common/ui/widgets/common/form/input/TextInput.vue';
import PasswordInput from '@/modules/common/ui/widgets/common/form/input/PasswordInput.vue';
import NumberInput from '@/modules/common/ui/widgets/common/form/input/NumberInput.vue';
import DateInput from '@/modules/common/ui/widgets/common/form/input/DateInput.vue';
import EmailInput from '@/modules/common/ui/widgets/common/form/input/EmailInput.vue';
import PhoneInput from '@/modules/common/ui/widgets/common/form/input/PhoneInput.vue';

import TextInputGroup from '@/modules/common/ui/widgets/common/form/input/TextInputGroup.vue';

import InputField from '@/modules/common/ui/widgets/common/form/field/InputField.vue';
import TextInputField from '@/modules/common/ui/widgets/common/form/field/TextInputField.vue';

import TextInputGroupField from '@/modules/common/ui/widgets/common/form/field/TextInputGroupField.vue';

export default {
    install: (app: Application, options: EzWidgetOptions) => {
        app.component("ez-input", Input);
        app.component("ez-text-input", TextInput);
        app.component("ez-password-input", PasswordInput);
        app.component("ez-number-input", NumberInput);
        app.component("ez-date-input", DateInput);
        app.component("ez-email-input", EmailInput);
        app.component("ez-phone-input", PhoneInput);

        app.component("ez-text-input-group", TextInputGroup);

        app.component("ez-input-field", InputField);
        app.component("ez-text-input-field", TextInputField);

        app.component("ez-text-input-group-field", TextInputGroupField);
    }
};

interface EzWidgetOptions {
    
};