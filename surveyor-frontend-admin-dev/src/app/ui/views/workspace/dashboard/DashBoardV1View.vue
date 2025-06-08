<template>
    <section>
        <div class="col-md-12">
            <div class="card card-navy">
                <!-- card-header -->
                <div class="card-header">
                    <h5 class="card-title">ค่าความกระเพื่อมของทรัพย์สินเชิงควอนตัม</h5>

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
                        <div class="col-md-8">
                            <p class="text-center">
                                <strong>01/05/2021 - 31/06/2021</strong>
                            </p>

                            <div class="chart">
                                <canvas id="fishsixLineChart"></canvas>
                            </div>
                            <!-- /.chart-responsive -->
                        </div>
                        <!-- /.col -->
                        <div class="col-md-4">
                            <p class="text-center">
                                <strong>อนุภาค Bosons</strong>
                            </p>

                            <div class="progress-group mt-4">
                                Photon
                                <span class="float-right"><b>160</b>/200</span>
                                <div class="progress progress-sm">
                                    <div class="progress-bar bg-primary" style="width: 80%"></div>
                                </div>
                            </div>
                            <!-- /.progress-group -->

                            <div class="progress-group mt-4">
                                Gluon
                                <span class="float-right"><b>310</b>/400</span>
                                <div class="progress progress-sm">
                                    <div class="progress-bar bg-danger" style="width: 75%"></div>
                                </div>
                            </div>

                            <!-- /.progress-group -->
                            <div class="progress-group mt-4">
                                <span class="progress-text">Z-BOSON</span>
                                <span class="float-right"><b>480</b>/800</span>
                                <div class="progress progress-sm">
                                    <div class="progress-bar bg-success" style="width: 60%"></div>
                                </div>
                            </div>

                            <div class="mt-4">
                                <canvas id="fishsixDoughnutChart"></canvas>
                            </div>
                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- /.row -->
                </div>
                <!-- ./card-body -->

                <div class="card-footer">
                    <div class="row">
                        <div class="col-sm-3 col-6">
                            <div class="description-block border-right">
                                <span class="description-percentage text-success">
                                    <i class="fas fa-caret-up"></i> 17%
                                </span>
                                <h5 class="description-header">35,210.43</h5>
                                <span class="description-text">Quark UP</span>
                            </div>
                        </div>

                        <div class="col-sm-3 col-6">
                            <div class="description-block border-right">
                                <span class="description-percentage text-warning">
                                    <i class="fas fa-caret-left"></i> 0%
                                </span>
                                <h5 class="description-header">10,390.90</h5>
                                <span class="description-text">Quark DOWN</span>
                            </div>
                        </div>

                        <div class="col-sm-3 col-6">
                            <div class="description-block border-right">
                                <span class="description-percentage text-success">
                                    <i class="fas fa-caret-up"></i> 20%
                                </span>
                                <h5 class="description-header">24,813.53</h5>
                                <span class="description-text">Quark TOP</span>
                            </div>
                        </div>

                        <div class="col-sm-3 col-6">
                            <div class="description-block">
                                <span class="description-percentage text-danger">
                                    <i class="fas fa-caret-down"></i> 18%
                                </span>
                                <h5 class="description-header">1200</h5>
                                <span class="description-text">Quark BOTTOM</span>
                            </div>
                        </div>
                    </div>
                    <!-- /.row -->
                </div>
                <!-- /.card-footer -->
            </div>
            <!-- /.card -->
        </div>
    </section>
</template>

<script lang="ts" setup>
    // @ts-nocheck

    import "https://cdn.jsdelivr.net/npm/chart.js@3.3.0/dist/chart.min.js";
    import { onMounted } from "vue";

    onMounted(() => {
        buildFishsixLineChart();
        buildFishsixDoughnutChart();
    });

    function buildFishsixLineChart()
    {
        const labels = [
            'January',
            'February',
            'March',
            'April',
            'May',
            'June'
        ];

        const data = {
            labels: [
                'January',
                'February',
                'March',
                'April',
                'May',
                'June'
            ],

            datasets: [
                {
                    label: 'Proton',
                    backgroundColor: '#dc3545',
                    borderColor: '#dc3545',
                    data: [30, 19, 25, 42, 20, 30, 15],
                },

                {
                    label: 'Nuetron',
                    backgroundColor: '#007bff',
                    borderColor: '#007bff',
                    data: [20, 39, 51, 12, 30, 37, 10],
                },

                {
                    label: 'Electron',
                    backgroundColor: '#28a745',
                    borderColor: '#28a745',
                    data: [50, 49, 85, 62, 34, 27, 30],
                }
            ]
        };

        const config = {
            type: 'line',
            data,
            options: {}
        };

        const fishsixLineChart = new Chart(document.getElementById('fishsixLineChart'), config);
    }

    function buildFishsixDoughnutChart()
    {
        const data = {
            labels: [
                'Proton',
                'Nuetron',
                'Electron'
            ],

            datasets: [
                {
                    label: 'My First Dataset',
                    data: [300, 50, 100],
                    backgroundColor: [
                        '#dc3545',
                        '#007bff',
                        '#28a745'
                    ],
                    hoverOffset: 4
                }
            ]
        };

        const config = {
            type: 'doughnut',
            data,
            options: {
                legend: {
                    position: 'bottom'
                }
            }
        }

        const fishsixDoughnutChart = new Chart(document.getElementById("fishsixDoughnutChart"), config);
    }
</script>