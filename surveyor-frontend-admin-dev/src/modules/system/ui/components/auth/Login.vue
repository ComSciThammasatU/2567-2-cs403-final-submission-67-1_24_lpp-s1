<template>
    <div id="loginApp" class="login-box">
        <div v-if="state.isLoading" class="ezw-loader">
            <div class='ezw-loader-spin'></div>
        </div>

        <div class="login-logo text-center">
            <img :src="AssetLoader.getAppTUSurveyorLogoImgURL()" alt="TU Surveyor Admin Logo" style="width: 150px;" class="brand-image elevation-0">
        </div>
        <!-- /.login-logo -->
        <div class="card">
            <div class="card-body login-card-body">
                <p class="login-box-msg text-bold" style="font-size: 1.1em; color: #2f3542;">เข้าสู่ระบบ</p>

                <div class="form-group">
                    <label>Username</label>
                    <div class="input-group mb-3">
                        <input  type="text" 
                                id="username" 
                                class="form-control" 
                                :class="{ 'is-invalid': !state.formValidate.username.valid }"
                                placeholder="Enter username" 
                                v-model="state.formData.username" 
                                @keyup.enter="login()" />
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-user"></span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <div class="input-group mb-3">
                        <input  type="password" 
                                id="password" 
                                class="form-control" 
                                :class="{ 'is-invalid': !state.formValidate.password.valid }"
                                placeholder="Enter password" 
                                v-model="state.formData.password" 
                                @keyup.enter="login()" />
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-lock"></span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <button class="btn btn-block btn-dark" @click="login()">Login</button>
                </div>

                <!-- <div class="form-group text-right">
                    <router-link :to="ForgotPasswordRoute.getRoutePath()" style="text-decoration: underline;">
                        ลืมรหัสผ่าน?
                    </router-link>
                </div> -->
            </div>
            <!-- /.login-card-body -->
        </div>
    </div>
    <!-- /.login-box -->
</template>

<script setup lang="ts">
    import { onMounted, reactive } from 'vue';
    import { useAuthStore } from '@/app/store/auth.store';
    import AssetLoader from '@/app/util/asset-loader';
    import Auth from '@/modules/core/service/auth.service';
    import AppInfoProvider from '@/app/config/app-info-provider';
    import HomeRoute from '@/app/router/routes/workspace/home.route';
    // import ForgotPasswordRoute from '@/app/router/routes/forgot-password.route';

    const props = defineProps({
        callbackUrl: {
            type: String,
            default: HomeRoute.getRoutePath()
        }
    });

    const appStore = useAuthStore();

    const state = reactive({
        isLoading: false,

        formData: {
            username: '',
            password: ''
        },

        formValidate: {
            username: { valid: true, errorMessage: '' },
            password: { valid: true, errorMessage: '' }
        }
    });

    onMounted(() => {
        console.log("### Login.onMounted ###");

        document.body.classList.add("hold-transition", "login-page");
    });

    function login()
    {
        console.log("login..");

        if(!validate()) {
            return;
        }

        const requestMessage: Auth.LoginRequestMessage = {
            clientAppInfo: {
                name: AppInfoProvider.getAppName(),
                version: AppInfoProvider.getAppVersion(),
                platform: "web"
            },
            username: state.formData.username,
            password: state.formData.password
        };
        
        appStore.login(requestMessage, props.callbackUrl);
    }

    function validate(): boolean
    {
        let valid = true;

        if(state.formData.username.trim() === '') {
            state.formValidate.username.valid = false;
            valid = false;
        } else {
            state.formValidate.username.valid = true;
        }

        if(state.formData.password.trim() === '') {
            state.formValidate.password.valid = false;
            valid = false;
        } else {
            state.formValidate.password.valid = true;
        }

        return valid;
    }
</script>

<style scoped>
    #loginApp {
        margin-top: 30%;
    }
</style>