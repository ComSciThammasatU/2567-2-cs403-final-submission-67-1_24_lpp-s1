export interface InputProps {
    type: "text" | "password" | "number" | "date" | "email" | "phone"
    modelValue?: string | number;
    size?: "s" | "m" | "l",
    state?: "normal" | "valid" | "warning" | "invalid"
};