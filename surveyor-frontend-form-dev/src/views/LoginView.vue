<template>
    <div class="login-box">
        <!-- /.login-logo -->
        <div class="card card-outline card-primary">
            <div class="card-header text-center">
                <a href="#" class="h1"><b>TU Surveyor</b></a>
            </div>
            <div class="card-body">
                <p class="login-box-msg text-maroon">Login เข้าสู่ระบบ</p>

                <form method="post">
                    <div class="input-group mb-3">
                        <input type="email" class="form-control" placeholder="Username" v-model="state.form.username">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-envelope"></span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="input-group mb-3">
                        <input type="password" class="form-control" placeholder="Password" v-model="state.form.password">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-lock"></span>
                            </div>
                        </div>
                    </div>

                    <div v-if="false" class="row">
                        <div class="col-8">
                            <div class="icheck-primary">
                                <input type="checkbox" id="remember">
                                <label for="remember">
                                    Remember Me
                                </label>
                            </div>
                        </div>
                        <!-- /.col -->
                        <div class="col-4">
                            <button type="submit" class="btn btn-primary btn-block">Sign In</button>
                        </div>
                        <!-- /.col -->
                    </div>
                </form>

                <div class="social-auth-links text-center mt-2 mb-3">
                    <button type="submit" class="btn btn-outline-primary btn-block" @click="login()">Sign In</button>
                </div>
                <!-- /.social-auth-links -->

                <p v-if="false" class="mb-1">
                    <a href="forgot-password.html">I forgot my password</a>
                </p>
                <p class="mb-0 text-center">
                    <router-link to="/register" class="text-warning">Register New Student</router-link>
                </p>
            </div>
            <!-- /.card-body -->
        </div>
        <!-- /.card -->
    </div>
</template>

<script setup lang="ts">
    import { onMounted, reactive } from 'vue';
    import { useRouter } from 'vue-router';
    import authService from '../services/auth.service';
    import { StatusType } from '../models/http.response-message.model';

    const router = useRouter();

    const state = reactive({
        form: {
            username: '',
            password: ''
        }
    });

    onMounted(async () => {
        await init();
    });

    async function init()
    {
        console.info("### LoginView.init ###");
    }

    async function login()
    {
        console.info("### LoginView.login ###");

        if(! validate()) {
            return;
        }

        try {
            const response = await authService.login({
                username: state.form.username,
                password: state.form.password
            });

            if(response.head.status.type === StatusType.Success) {
                router.push('/');
            } else {
                alert("Login ไม่สำเร็จ เนื่องจาก " + response.head.status.message);
            }
        } catch(error: any) {
            alert("ERROR : " + error.message);
        }
    }

    function validate(): boolean
    {
        console.info("### LoginView.validate ###");
        
        if(! state.form.username) {
            alert("กรุณากรอก Username");
            return false;
        }

        if(! state.form.password) {
            alert("กรุณากรอก Password");
            return false;
        }  

        return true;
    }
</script>