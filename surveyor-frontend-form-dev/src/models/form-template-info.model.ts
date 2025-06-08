import type { FormTemplateAuthority } from "./form-template-authority.mode";
import  type { FormTemplateElement } from "./form-template-element.model";
import type { FormTemplate } from "./form-template.model";

export interface FormTemplateInfo {
    formTemplate?: FormTemplate;
    formTemplateElements?: FormTemplateElement[];
    formTemplateAuthorities?: FormTemplateAuthority[];
};