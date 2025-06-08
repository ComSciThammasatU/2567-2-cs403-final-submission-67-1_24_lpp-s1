<template>
    <div>
        <div v-if="false" class="row">
            <div class="col-12 col-sm-6 col-md-3">
                <div class="info-box">
                    <span class="info-box-icon bg-info elevation-1"><i class="fas fa-cog"></i></span>

                    <div class="info-box-content">
                        <span class="info-box-text">CPU Traffic</span>
                        <span class="info-box-number">
                            10
                            <small>%</small>
                        </span>
                    </div>
                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>
            <!-- /.col -->
            <div class="col-12 col-sm-6 col-md-3">
                <div class="info-box mb-3">
                    <span class="info-box-icon bg-danger elevation-1"><i class="fas fa-thumbs-up"></i></span>

                    <div class="info-box-content">
                        <span class="info-box-text">Likes</span>
                        <span class="info-box-number">41,410</span>
                    </div>
                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>
            <!-- /.col -->

            <!-- fix for small devices only -->
            <div class="clearfix hidden-md-up"></div>

            <div class="col-12 col-sm-6 col-md-3">
                <div class="info-box mb-3">
                    <span class="info-box-icon bg-success elevation-1"><i class="fas fa-shopping-cart"></i></span>

                    <div class="info-box-content">
                        <span class="info-box-text">Sales</span>
                        <span class="info-box-number">760</span>
                    </div>
                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>
            <!-- /.col -->
            <div class="col-12 col-sm-6 col-md-3">
                <div class="info-box mb-3">
                    <span class="info-box-icon bg-warning elevation-1"><i class="fas fa-users"></i></span>

                    <div class="info-box-content">
                        <span class="info-box-text">New Members</span>
                        <span class="info-box-number">2,000</span>
                    </div>
                    <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
            </div>
            <!-- /.col -->
        </div>

        <div class="row">
            <div class="col-lg-3 col-6">
                <!-- small box -->
                <div class="small-box bg-info">
                    <div class="inner">
                        <h3>125</h3>

                        <p>Completed Task</p>
                    </div>
                    <div class="icon">
                        <i class="fas fa-tasks"></i>
                    </div>
                    <a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
            <div class="col-lg-3 col-6">
                <!-- small box -->
                <div class="small-box bg-warning">
                    <div class="inner">
                        <h3>16</h3>

                        <p>Pending Task</p>
                    </div>
                    <div class="icon">
                        <!-- <i class="ion ion-bag"></i> -->
                        <i class="fas fa-spinner"></i>
                    </div>
                    <a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
            <div class="col-lg-3 col-6">
                <!-- small box -->
                <div class="small-box bg-success">
                    <div class="inner">
                        <h3>73<sup style="font-size: 20px">%</sup></h3>

                        <p>Approved Task</p>
                    </div>
                    <div class="icon">
                        <!-- <i class="ion ion-stats-bars"></i> -->
                        <i class="fas fa-check"></i>
                    </div>
                    <a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
            <div class="col-lg-3 col-6">
                <!-- small box -->
                <div class="small-box bg-danger">
                    <div class="inner">
                        <!-- <h3>65</h3> -->
                        <h3>27<sup style="font-size: 20px">%</sup></h3>

                        <p>Rejected Task</p>
                    </div>
                    <div class="icon">
                        <!-- <i class="ion ion-pie-graph"></i> -->
                        <i class="fas fa-times"></i>
                    </div>
                    <a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
        </div>

        <div class="row">
            <div class="col-md-9">
                <div class="card card-navy">
                    <!--
                    <div class="card-header border-transparent">
                        <h3 class="card-title">Summary Work List Status</h3>
                    
                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                <i class="fas fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    -->
                    <!-- /.card-header -->
                    <!-- <div class="card-body p-0">
                        <div class="p-2">
                            <div id="chartdiv"></div>
                        </div>
                    </div> -->
                    <div class="card-header border-transparent" style="height: 45px;">
                        <h3 class="card-title">Summary Work List Status</h3>
                    </div>
                    <div id="chartdiv"></div>
                </div>

                <div class="card card-navy">
                    <div class="card-header border-transparent">
                        <h3 class="card-title">{{ i18nComposer.getLabel('card.rencent_worklist.header.title') }}</h3>
                    
                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                <i class="fas fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table m-0">
                                <thead>
                                    <tr>
                                        <th>{{ i18nComposer.getLabel('table.header.request_no') }}</th>
                                        <th>{{ i18nComposer.getLabel('table.header.task') }}</th>
                                        <th>{{ i18nComposer.getLabel('table.header.requestor') }}</th>
                                        <th>{{ i18nComposer.getLabel('table.header.assigned_date') }}</th>
                                        <th>{{ i18nComposer.getLabel('table.header.worklist_status') }}</th>
                                        <th>{{ i18nComposer.getLabel('table.header.task_status') }}</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="worklist in state.myWorklist" :key="worklist.requestNo">
                                        <td class="align-middle">
                                            <a href="javascript:void(0)" class="text-primary">{{ worklist.requestNo }}</a>
                                        </td>
                                        <td class="align-middle">{{ worklist.taskName }}</td>
                                        <td class="align-middle">{{ worklist.requestor }}</td>
                                        <td class="align-middle">{{ worklist.assignedDate }}</td>
                                        <td class="text-center align-middle">
                                            <span :class="getWorklistBadgeClasses(worklist)">{{ worklist.worklistStatus }}</span>
                                        </td>
                                        <td class="align-middle">
                                            {{ worklist.taskStatus }}
                                        </td>
                                        <td class="align-middle">
                                            <a href="" class="btn btn-sm btn-outline-primary">
                                                <i class="fas fa-chevron-right"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.table-responsive -->
                    </div>
                    <!-- /.card-body -->
                </div>

                <div class="card card-navy">
                    <div class="card-header border-transparent">
                        <h3 class="card-title">Worklist History</h3>
                    
                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                <i class="fas fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body p-2">
                        <div class="row">
                            <div class="col-md-12">
                        
                                <div class="timeline">
                        
                                    <div class="time-label">
                                        <span class="bg-red">10 Feb. 2014</span>
                                    </div>
                        
                                    <div>
                                        <!-- <i class="fas fa-envelope bg-blue"></i> -->
                                        <div class="timeline-item">
                                            <span class="time"><i class="fas fa-clock"></i> 12:05</span>
                                            <h3 class="timeline-header"><a href="#">Create Plan</a></h3>

                                            <div class="timeline-body">
                                                <div class="table-responsive">
                                                    <table class="table table-hover text-nowrap">
                                                        <thead>
                                                            <tr>
                                                                <th>Responsiable</th>
                                                                <th>Request No</th>
                                                                <th>Task Status</th>
                                                                <th>Action Date</th>
                                                                <th style="width: 64px;">Action</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <tr>
                                                                <td class="align-middle">Andrei Shevchemko</td>
                                                                <td class="align-middle">2022100300001</td>
                                                                <td class="align-middle">
                                                                    <span class="badge badge-success">Created</span>
                                                                </td>
                                                                <td>10/10/2022</td>
                                                                <td class="align-middle text-center">
                                                                    <button type="button" class="btn btn-sm btn-outline-primary mx1" title="แก้ไขข้อมูล">
                                                                        <i class="fas fa-chevron-right"></i>
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>

                                            <div class="timeline-footer card-footer text-right">
                                                <a class="btn btn-primary btn-sm">More Detail</a>
                                                <!-- &nbsp; -->
                                                <!-- <a class="btn btn-danger btn-sm">View</a> -->
                                            </div>
                                        </div>
                                    </div>

                                    <div class="time-label">
                                        <span class="bg-red">10 Feb. 2014</span>
                                    </div>
                        
                        
                                    <div>
                                        <!-- <i class="fas fa-envelope bg-blue"></i> -->
                                        <div class="timeline-item">
                                            <span class="time"><i class="fas fa-clock"></i> 12:05</span>
                                            <h3 class="timeline-header"><a href="#">Submit Count</a></h3>

                                            <div class="timeline-body">
                                                <div class="table-responsive">
                                                    <table class="table table-hover text-nowrap">
                                                        <thead>
                                                            <tr>
                                                                <th>Responsiable</th>
                                                                <th>Request No</th>
                                                                <th>Task Status</th>
                                                                <td>Action Date</td>
                                                                <th style="width: 64px;">Action</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <tr>
                                                                <td class="align-middle">Andrei Shevchemko</td>
                                                                <td class="align-middle">2022100300001</td>
                                                                <!-- <td class="align-middle">Created</td> -->
                                                                <td class="align-middle">
                                                                    <span class="badge badge-danger">Submit Count</span>
                                                                </td>
                                                                <td>10/10/2022</td>
                                                                <td class="align-middle text-center">
                                                                    <button type="button" class="btn btn-sm btn-outline-primary mx1" title="แก้ไขข้อมูล">
                                                                        <i class="fas fa-chevron-right"></i>
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="align-middle">Andrei Shevchemko</td>
                                                                <td class="align-middle">2022100300001</td>
                                                                <!-- <td class="align-middle">Created</td> -->
                                                                <td class="align-middle">
                                                                    <span class="badge badge-danger">Submit Count</span>
                                                                </td>
                                                                <td>10/10/2022</td>
                                                                <td class="align-middle text-center">
                                                                    <button type="button" class="btn btn-sm btn-outline-primary mx1" title="แก้ไขข้อมูล">
                                                                        <i class="fas fa-chevron-right"></i>
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>

                                            <div class="timeline-footer card-footer text-right">
                                                <a class="btn btn-primary btn-sm">More Detail</a>
                                                <!-- &nbsp; -->
                                                <!-- <a class="btn btn-danger btn-sm">View</a> -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <!-- Widget: user widget style 1 -->
                <div class="card card-widget widget-user shadow">
                    <!-- Add the bg color to the header using any of the bg-* classes -->
                    <div class="widget-user-header bg-info">
                        <h3 class="widget-user-username">Alexander Pierce</h3>
                        <h5 class="widget-user-desc">Founder & CEO</h5>
                    </div>
                    <div class="widget-user-image">
                        <img class="img-circle elevation-2" src="https://adminlte.io/themes/v3/dist/img/user1-128x128.jpg" alt="User Avatar">
                    </div>
                    <div class="card-footer">
                        <div class="row">
                            <div class="col-sm-4 border-right">
                                <div class="description-block">
                                    <h5 class="description-header">3,200</h5>
                                    <span class="description-text">SALES</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-4 border-right">
                                <div class="description-block">
                                    <h5 class="description-header">13,000</h5>
                                    <span class="description-text">FOLLOWERS</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-4">
                                <div class="description-block">
                                    <h5 class="description-header">35</h5>
                                    <span class="description-text">PRODUCTS</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- /.row -->
                    </div>
                </div>
                <!-- /.widget-user -->

                <div class="card card-navy">
                    <!-- card-header -->
                    <div class="card-header">
                        <h5 class="card-title">Task Progress</h5>

                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                <i class="fas fa-minus"></i>
                            </button>
                            <div class="btn-group">
                                <button type="button" class="btn btn-tool dropdown-toggle" data-toggle="dropdown">
                                    <i class="fas fa-wrench"></i>
                                </button>
                                <div class="dropdown-menu dropdown-menu-right" role="menu">
                                    <a href="#" class="dropdown-item">Action</a>
                                    <a href="#" class="dropdown-item">Another action</a>
                                    <a href="#" class="dropdown-item">Something else here</a>
                                    <a class="dropdown-divider"></a>
                                    <a href="#" class="dropdown-item">Separated link</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.card-header -->

                    <!-- card-body -->
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-12">
                                <p class="text-center">
                                    <strong>Do To Task</strong>
                                </p>

                                <div class="progress-group">
                                    Physical Count
                                    <span class="float-right"><b>160</b>/200</span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-primary" style="width: 80%"></div>
                                    </div>
                                </div>
                                <!-- /.progress-group -->

                                <div class="progress-group">
                                    Transfer
                                    <span class="float-right"><b>310</b>/400</span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-danger" style="width: 75%"></div>
                                    </div>
                                </div>

                                <!-- /.progress-group -->
                                <div class="progress-group">
                                    <span class="progress-text">Borrow</span>
                                    <span class="float-right"><b>480</b>/800</span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-success" style="width: 60%"></div>
                                    </div>
                                </div>

                                <!-- /.progress-group -->
                                <div class="progress-group">
                                    Retirement
                                    <span class="float-right"><b>250</b>/500</span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-warning" style="width: 50%"></div>
                                    </div>
                                </div>
                                <!-- /.progress-group -->
                            </div>
                        </div>

                        <hr>

                        <div class="row">
                            <div class="col-md-12">
                                <p class="text-center">
                                    <strong>Do Approve Task</strong>
                                </p>

                                <div class="progress-group">
                                    Physical Count
                                    <span class="float-right"><b>160</b>/200</span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-primary" style="width: 80%"></div>
                                    </div>
                                </div>
                                <!-- /.progress-group -->

                                <div class="progress-group">
                                    Transfer
                                    <span class="float-right"><b>310</b>/400</span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-danger" style="width: 75%"></div>
                                    </div>
                                </div>

                                <!-- /.progress-group -->
                                <div class="progress-group">
                                    <span class="progress-text">Borrow</span>
                                    <span class="float-right"><b>480</b>/800</span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-success" style="width: 60%"></div>
                                    </div>
                                </div>

                                <!-- /.progress-group -->
                                <div class="progress-group">
                                    Retirement
                                    <span class="float-right"><b>250</b>/500</span>
                                    <div class="progress progress-sm">
                                        <div class="progress-bar bg-warning" style="width: 50%"></div>
                                    </div>
                                </div>
                                <!-- /.progress-group -->
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card card-navy">
                    <div class="card-header">
                        <h3 class="card-title">Recent Notification</h3>
                
                        <div class="card-tools">
                            <button type="button" class="btn btn-tool" data-card-widget="collapse">
                                <i class="fas fa-minus"></i>
                            </button>
                        </div>
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body p-0">
                        <ul class="products-list product-list-in-card pl-2 pr-2">
                            <li class="item">
                                <div class="product-img">
                                    <img src="https://via.placeholder.com/150" alt="Product Image" class="img-size-50">
                                </div>
                                <div class="product-info">
                                    <a href="javascript:void(0)" class="product-title">
                                        Physical Count
                                        <span class="badge badge-danger float-right">15/10/2022</span>
                                    </a>
                                    <div class="">
                                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Nemo sit repudiandae laboriosam consequatur placeat.
                                    </div>
                                </div>
                            </li>
                            <!-- /.item -->
                            <li class="item">
                                <div class="product-img">
                                    <img src="https://via.placeholder.com/150" alt="Product Image" class="img-size-50">
                                </div>
                                <div class="product-info">
                                    <a href="javascript:void(0)" class="product-title">
                                        Physical Count
                                        <span class="badge badge-danger float-right">15/10/2022</span>
                                    </a>
                                    <div class="">
                                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Nemo sit repudiandae laboriosam consequatur placeat.
                                    </div>
                                </div>
                            </li>
                            <!-- /.item -->
                            <li class="item">
                                <div class="product-img">
                                    <img src="https://via.placeholder.com/150" alt="Product Image" class="img-size-50">
                                </div>
                                <div class="product-info">
                                    <a href="javascript:void(0)" class="product-title">
                                        Physical Count
                                        <span class="badge badge-danger float-right">15/10/2022</span>
                                    </a>
                                    <div class="">
                                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Nemo sit repudiandae laboriosam consequatur placeat.
                                    </div>
                                </div>
                            </li>
                            <!-- /.item -->
                            <li class="item">
                                <div class="product-img">
                                    <img src="https://via.placeholder.com/150" alt="Product Image" class="img-size-50">
                                </div>
                                <div class="product-info">
                                    <a href="javascript:void(0)" class="product-title">
                                        Physical Count
                                        <span class="badge badge-danger float-right">15/10/2022</span>
                                    </a>
                                    <div class="">
                                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Nemo sit repudiandae laboriosam consequatur placeat.
                                    </div>
                                </div>
                            </li>
                            <!-- /.item -->
                        </ul>
                    </div>
                    <!-- /.card-body -->
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    // @ts-nocheck

    import "https://cdn.amcharts.com/lib/5/index.js";
    import "https://cdn.amcharts.com/lib/5/xy.js";
    import "https://cdn.amcharts.com/lib/5/percent.js";
    import "https://cdn.amcharts.com/lib/5/themes/Animated.js";
    import { onMounted, reactive } from "vue";
    import { Label } from "@/modules/common/model/label.model";

    interface Worklist {
        requestNo: string;
        taskName: string;
        requestor: string;
        assignedDate: string;
        worklistStatus: "ToDo" | "ToApprove" | "Done";
        taskStatus: string;
    };

    const myWorklist: Worklist[] = [
        {
            requestNo: 'REQ7501',
            taskName: 'Physical Count',
            requestor: 'Frodo Baggins',
            assignedDate: '02/10/2022',
            worklistStatus: 'Done',
            taskStatus: 'Count Completed'
        },

        {
            requestNo: 'REQ7502',
            taskName: 'Physical Count',
            requestor: 'Gandaf',
            assignedDate: '03/10/2022',
            worklistStatus: 'ToDo',
            taskStatus: 'In-progress'
        },

        {
            requestNo: 'REQ7503',
            taskName: 'Physical Count',
            requestor: 'Teoden',
            assignedDate: '03/10/2022',
            worklistStatus: 'ToApprove',
            taskStatus: 'In-progress'
        },

        {
            requestNo: 'REQ7501',
            taskName: 'Physical Count',
            requestor: 'Frodo Baggins',
            assignedDate: '02/10/2022',
            worklistStatus: 'Done',
            taskStatus: 'Count Completed'
        },

        {
            requestNo: 'REQ7502',
            taskName: 'Physical Count',
            requestor: 'Gandaf',
            assignedDate: '03/10/2022',
            worklistStatus: 'ToDo',
            taskStatus: 'In-progress'
        },

        {
            requestNo: 'REQ7503',
            taskName: 'Physical Count',
            requestor: 'Teoden',
            assignedDate: '03/10/2022',
            worklistStatus: 'ToApprove',
            taskStatus: 'In-progress'
        },

        {
            requestNo: 'REQ7501',
            taskName: 'Physical Count',
            requestor: 'Frodo Baggins',
            assignedDate: '02/10/2022',
            worklistStatus: 'Done',
            taskStatus: 'Count Completed'
        },

        {
            requestNo: 'REQ7502',
            taskName: 'Physical Count',
            requestor: 'Gandaf',
            assignedDate: '03/10/2022',
            worklistStatus: 'ToDo',
            taskStatus: 'In-progress'
        },

        {
            requestNo: 'REQ7503',
            taskName: 'Physical Count',
            requestor: 'Teoden',
            assignedDate: '03/10/2022',
            worklistStatus: 'ToApprove',
            taskStatus: 'In-progress'
        }
    ];

    const labels: Label[] = [
        {
            key: 'card.rencent_worklist.header.title',
            value: 'Recent Work List',
            i18ns: [
                {
                    langCode: 'en',
                    value: 'Recent Work List'
                },
                {
                    langCode: 'th',
                    value: 'เวิร์คลิสล่าสุด'
                }
            ]
        },

        {
            key: 'table.header.request_no',
            value: 'Request No',
            i18ns: [
                {
                    langCode: 'en',
                    value: 'Request No'
                },
                {
                    langCode: 'th',
                    value: 'เลขคำร้อง'
                }
            ]
        },

        {
            key: 'table.header.task',
            value: 'Task',
            i18ns: [
                {
                    langCode: 'en',
                    value: 'Task'
                },
                {
                    langCode: 'th',
                    value: 'งาน'
                }
            ]
        },

        {
            key: 'table.header.requestor',
            value: 'Requestor',
            i18ns: [
                {
                    langCode: 'en',
                    value: 'Requestor'
                },
                {
                    langCode: 'th',
                    value: 'ผู้ร้อง'
                }
            ]
        },

        {
            key: 'table.header.assigned_date',
            value: 'Assign Date',
            i18ns: [
                {
                    langCode: 'en',
                    value: 'Assign Date'
                },
                {
                    langCode: 'th',
                    value: 'วันที่มอบหมาย'
                }
            ]
        },

        {
            key: 'table.header.worklist_status',
            value: 'Worklist Status',
            i18ns: [
                {
                    langCode: 'en',
                    value: 'Worklist Status'
                },
                {
                    langCode: 'th',
                    value: 'สถานะเวิร์คลิส'
                }
            ]
        },

        {
            key: 'table.header.task_status',
            value: 'Task Status',
            i18ns: [
                {
                    langCode: 'en',
                    value: 'Task Status'
                },
                {
                    langCode: 'th',
                    value: 'สถานะงาน'
                }
            ]
        }
    ];

    const state = reactive({
        myWorklist,
        labels
    });

    const i18nComposer = useI18nComposer(state.labels);

    onMounted(() => {
        buildChart();
    });

    function getWorklistBadgeClasses(worklist: Worklist): string
    {
        if(worklist.worklistStatus === "ToDo") {
            return "badge badge-warning";
        } else if(worklist.worklistStatus === "ToApprove") {
            return "badge badge-danger";
        } if(worklist.worklistStatus === "Done") {
            return "badge badge-success";
        }
        return "badge";
    }

    function buildChart() {
        console.log("### DashboardV2.buildChart ###");

        am5.ready(function () {
            console.log("am5.ready");

            // Set up data
            var data = [
                {
                    category: "To-approve",
                    value: 89,
                    sliceSettings: {
                        fill: am5.color('#e74c3c'),
                    },
                    breakdown: [
                        {
                            category: "Physical Count",
                            value: 29
                        }, {
                            category: "Transfer",
                            value: 40
                        }, {
                            category: "Borrow",
                            value: 11
                        }, {
                            category: "Retirement",
                            value: 9
                        }
                    ]
                },
                {
                    category: "To-do",
                    value: 71,
                    sliceSettings: {
                        fill: am5.color('#f1c40f'),
                    },
                    breakdown: [{
                        category: "Physical Count",
                        value: 22
                    }, {
                        category: "Transfer",
                        value: 30
                    }, {
                        category: "Borrow",
                        value: 11
                    }, {
                        category: "Retirement",
                        value: 10
                    }]
                }, 
                {
                    category: "Done",
                    value: 120,
                    sliceSettings: {
                        fill: am5.color('#1abc9c'),
                    },
                    breakdown: [{
                        category: "Physical Count",
                        value: 60
                    }, {
                        category: "Transfer",
                        value: 35
                    }, {
                        category: "Borrow",
                        value: 15
                    }, {
                        category: "Retirement",
                        value: 10
                    }]
                }
            ];

            // Create root element
            // https://www.amcharts.com/docs/v5/getting-started/#Root_element
            var root = am5.Root.new("chartdiv");


            // Set themes
            // https://www.amcharts.com/docs/v5/concepts/themes/
            root.setThemes([
                am5themes_Animated.new(root)
            ]);

            // Create wrapper container
            var container = root.container.children.push(am5.Container.new(root, {
                width: am5.p100,
                height: am5.p100,
                layout: root.horizontalLayout
            }));


            // ==============================================
            // Column chart
            // ==============================================

            // Create chart
            // https://www.amcharts.com/docs/v5/charts/xy-chart/
            var columnChart = container.children.push(am5xy.XYChart.new(root, {
                width: am5.p50,
                panX: false,
                panY: false,
                wheelX: "none",
                wheelY: "none",
                layout: root.verticalLayout
            }));

            // Create axes
            // https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
            var yAxis = columnChart.yAxes.push(am5xy.CategoryAxis.new(root, {
                categoryField: "category",
                renderer: am5xy.AxisRendererY.new(root, {})
            }));

            //xAxis.data.setAll(data);

            var xAxis = columnChart.xAxes.push(am5xy.ValueAxis.new(root, {
                renderer: am5xy.AxisRendererX.new(root, {})
            }));


            // Add series
            // https://www.amcharts.com/docs/v5/charts/xy-chart/series/
            var columnSeries = columnChart.series.push(am5xy.ColumnSeries.new(root, {
                name: name,
                xAxis: xAxis,
                yAxis: yAxis,
                valueXField: "value",
                categoryYField: "category"
            }));

            columnSeries.columns.template.setAll({
                tooltipText: "{categoryY}: {valueX}"
            });

            //series.data.setAll(data);

            // Make stuff animate on load
            // https://www.amcharts.com/docs/v5/concepts/animations/
            columnChart.appear(1000, 100);


            // ==============================================
            // Column chart
            // ==============================================

            var pieChart = container.children.push(
                am5percent.PieChart.new(root, {
                    width: am5.p50,
                    innerRadius: am5.percent(50)
                })
            );

            // Create series
            var pieSeries = pieChart.series.push(
                am5percent.PieSeries.new(root, {
                    valueField: "value",
                    categoryField: "category"
                })
            );

            pieSeries.slices.template.setAll({
                templateField: "sliceSettings",
                strokeOpacity: 0
            });

            var currentSlice;
            pieSeries.slices.template.on("active", function (active, slice) {
                if (currentSlice && currentSlice != slice && active) {
                    currentSlice.set("active", false)
                }

                var color = slice.get("fill");

                label1.setAll({
                    fill: color,
                    text: root.numberFormatter.format(slice.dataItem.get("valuePercentTotal"), "#.'%'")
                });

                label2.set("text", slice.dataItem.get("category"));

                columnSeries.columns.template.setAll({
                    fill: slice.get("fill"),
                    stroke: slice.get("fill")
                });

                columnSeries.data.setAll(slice.dataItem.dataContext.breakdown);
                yAxis.data.setAll(slice.dataItem.dataContext.breakdown);

                currentSlice = slice;
            });

            pieSeries.labels.template.set("forceHidden", true);
            pieSeries.ticks.template.set("forceHidden", true);

            pieSeries.data.setAll(data);

            // Add label
            var label1 = pieChart.seriesContainer.children.push(am5.Label.new(root, {
                text: "",
                fontSize: 35,
                fontweight: "bold",
                centerX: am5.p50,
                centerY: am5.p50
            }));

            var label2 = pieChart.seriesContainer.children.push(am5.Label.new(root, {
                text: "",
                fontSize: 12,
                centerX: am5.p50,
                centerY: am5.p50,
                dy: 30
            }));

            // Pre-select first slice
            pieSeries.events.on("datavalidated", function () {
                pieSeries.slices.getIndex(0).set("active", true);
            });

            setTimeout(() => {
                // updatePieData(pieSeries);
            }, 2000);
        });
    }

    function updatePieData(pieSeries)
    {
        pieSeries.data.push({
            category: "InProgreass",
            value: 120,
            sliceSettings: {
                fill: am5.color('#3498db'),
            },
            breakdown: [{
                category: "Physical Count",
                value: 60
            }, {
                category: "Transfer",
                value: 35
            }, {
                category: "Borrow",
                value: 15
            }, {
                category: "Retirement",
                value: 10
            }]
        });
    }
</script>

<style scoped>
    #chartdiv {
        width: 100%;
        height: 400px;
    }
</style>