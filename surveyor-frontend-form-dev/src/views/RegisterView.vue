<template>
    <div class="register-box">
        <div class="card card-outline card-primary">
            <div class="card-header text-center">
                <a href="#" class="h1 text-navy"><b>TU Surveyor</b></a>
            </div>
            <div class="card-body">
                <p class="login-box-msg text-maroon">ลงทะเบียนข้อมูลนักศึกษา</p>

                <form method="post">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="รหัสนักศึกษา" v-model="state.form.studentCode">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-user"></span>
                            </div>
                        </div>
                    </div>

                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="ชื่อ-นามสกุล" v-model="state.form.accountName">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-user"></span>
                            </div>
                        </div>
                    </div>

                    <div class="input-group mb-3">
                        <input type="email" class="form-control" placeholder="อีเมล" v-model="state.form.email">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-envelope"></span>
                            </div>
                        </div>
                    </div>

                    <div class="input-group mb-3">
                        <input type="password" class="form-control" placeholder="รหัสผ่าน" v-model="state.form.password">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-lock"></span>
                            </div>
                        </div>
                    </div>

                    <div class="input-group mb-3">
                        <input type="password" class="form-control" placeholder="ยืนยันรหัสผ่าน" v-model="state.form.confirmPassword">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-lock"></span>
                            </div>
                        </div>
                    </div>

                    <div class="input-group mb-3">
                        <select class="form-control select2bs4" style="width: 100%;" v-model="state.form.studyDegreeId">
                            <option value="" selected disabled>--- เลือกระดับการศึกษา ---</option>
                            <option value="Bachelor">Bachelor</option>
                            <option value="Master">Master</option>
                            <option value="Doctor">Doctor</option>
                        </select>
                    </div>

                    <div class="input-group mb-3">
                        <input type="number" class="form-control" placeholder="ชั้นปี" v-model="state.form.studyClassLevel">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-user"></span>
                            </div>
                        </div>
                    </div>
                </form>

                <div class="social-auth-links text-center">
                    <button type="submit" class="btn btn-outline-primary btn-block" @click="submit()">ยืนยัน</button>
                </div>

                <div class="social-auth-links text-center">
                    <router-link to="/login" class="text-warning">Login</router-link>
                </div>
            </div>
            <!-- /.form-box -->
        </div><!-- /.card -->
    </div>
    <!-- /.register-box -->
</template>

<script setup lang="ts">
    import { onMounted, reactive } from 'vue';
    import { useRouter } from 'vue-router';
    import liff from '@line/liff';
    import type { Profile as LineUserProfile } from '@liff/get-profile';
    import studentService from '../services/student.service';
    import { StatusType } from '../models/http.response-message.model';
    import EnvironmentProvider from '../services/env.provider';

    const router = useRouter();

    const state = reactive({
        form: {
            studentCode: '',
            accountName: '',
            email: '',
            username: '',
            password: '',
            confirmPassword: '',
            studyDegreeId: '',
            studyClassLevel: ''
        }
    });

    const LIFF_ID = EnvironmentProvider.getLiffId();
    let lineUserProfile: LineUserProfile;

    onMounted(async () => {
        await init();
    });

    async function init()
    {
        console.info("### RegisterView.init ###");

        await getLineUserProfile();

        const responseMessage = await studentService.loadRegisterScreen({});
        console.log(responseMessage);
    }

    async function getLineUserProfile()
    {
        console.info("### RegisterView.getLineUserProfile ###");

        console.log("LIFF_ID => " + LIFF_ID);

        try {
            await liff.init({ liffId: LIFF_ID });

            // ตรวจสอบว่ารันใน LIFF engine หรือไม่
            const context = liff.getContext();

            if(! context) {
                alert("Error, couldn't use liff");
                return;
            }

            const isLiffApp = context.type !== 'external'; // external = เปิดจาก browser :contentReference[oaicite:6]{index=6}

            console.log("context.type => ", context.type);
            console.log("isLiffApp => ", isLiffApp);

            if (isLiffApp) {
                // ถ้าเป็น LIFF: ดึง profile ได้ทันที
                lineUserProfile = await liff.getProfile();
            } else {
                // ถ้าไม่ใช่ LIFF: ให้ผู้ใช้ login ผ่าน LINE Login OAuth
                if (!liff.isInClient() && !liff.isLoggedIn()) {
                    console.log("redirectUri => ", window.location.href);
                    liff.login({ redirectUri: window.location.href });
                    return;
                }
                // หลัง login กลับมา, profile สามารถเรียกได้เช่นกัน
                lineUserProfile = await liff.getProfile();
            }

            console.log("lineUserProfile => ", lineUserProfile);
            sessionStorage.setItem('lineUserProfile', JSON.stringify(lineUserProfile));

        } catch (err) {
            console.error('LIFF init failed:', err);
            alert('LIFF init failed:' + err);
        }
    }

    async function submit()
    {
        console.info("### RegisterView.submit ###");

        if(! validate()) {
            return;
        }

        try {
            const response = await studentService.submit({
                registerStudentRequest: {
                    studentCode: state.form.studentCode,
                    accountName: state.form.accountName,
                    email: state.form.email,
                    username: state.form.studentCode,
                    password: state.form.password,
                    studyDegreeId: state.form.studyDegreeId,
                    studyClassLevel: state.form.studyClassLevel,
                    lineUserProfile: lineUserProfile
                }
            });

            if(response.head.status.type === StatusType.Success) {
                alert("ลงทะเบียนสำเร็จ");
                router.push('/login');
            } else {
                alert("ลงทะเบียนไม่สำเร็จ เนื่องจาก " + response.head.status.message);
            }
        } catch(error: any) {
            alert("ERROR : " + error.message);
        }
    }

    function validate(): boolean
    {
        if(state.form.studentCode === '') {
            alert('กรุณากรอกรหัสนักศึกษา');
            return false;
        }

        if(state.form.accountName === '') {
            alert('กรุณากรอกชื่อ-นามสกุล');
            return false;
        }

        if(state.form.email === '') {
            alert('กรุณากรอกอีเมล');
            return false;
        }

        if(state.form.password === '') {
            alert('กรุณากรอกรหัสผ่าน');
            return false;
        }

        if(state.form.confirmPassword === '') {
            alert('กรุณากรอกยืนยันรหัสผ่าน');
            return false;
        }

        if(state.form.password !== state.form.confirmPassword) {
            alert('รหัสผ่านไม่ตรงกัน');
            return false;
        }

        if(state.form.studyDegreeId === '') {
            alert('กรุณาเลือกระดับการศึกษา');
            return false;
        }

        if(state.form.studyClassLevel === '') {
            alert('กรุณากรอกชั้นปี');
            return false;
        }

        return true;
    }
</script>