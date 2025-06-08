import { SideBarMenuMode } from "@/modules/common/const/sidebar-menu-mode.const";
import { BreadcrumbType } from "@/modules/common/model/breadcrumb.model";
import { Action, HierachyType, IconType, ItemType, Menu } from "@/modules/common/model/menu.model";

class MockMenuService
{
    public async buildMockMenus(): Promise<Menu[]>
    {
        return new Promise<Menu[]>((resolve, reject) => {
            resolve([
                // ...this.buildMockDashboardMenus(),

                ...this.buildMockAssetSetupMenus(),

                ...this.buildMockAssetAcquisitionMenus(),

                ...this.buildMockAssetPhysicalCountMenus(),

                // ...this.buildMockTutorialMenus(),

                // ...this.buildMockSettingMenus(),

                ...this.buildMockFmsReportingMenus(),

                ...this.buildMockImportDataMenus(),

                ...this.buildMockSettingSystemMenus()
            ]);
        });
    }

    protected buildMockDashboardMenus(): Menu[]
    {
        return [
            {
                menuId: 'workspace.dashboard.section',
                itemType: ItemType.Section,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 2,
                title: 'Dashboard',
                titleI18ns: [
                    {
                        langCode: 'en',
                        value: 'Dashboard'
                    },
                    {
                        langCode: 'th',
                        value: 'แดชบอร์ด'
                    }
                ],
                description: 'Dashboard',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-cogs',
                action: Action.None,
                lazyLoad: false,
                active: false,
                children: []
            },

            {
                menuId: 'workspace.dashboard.menuDropdown',
                itemType: ItemType.MenuDropdown,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 3,
                title: 'Dashboard',
                titleI18ns: [
                    {
                        langCode: 'en',
                        value: 'Dashboard'
                    },
                    {
                        langCode: 'th',
                        value: 'แดชบอร์ด'
                    }
                ],
                description: 'Dashboard',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-tachometer-alt',
                action: Action.ToggleDropdown,
                href: '/workspace/dashboard',
                dropdownOpen: true,
                active: false,
                children: [
                    {
                        menuId: 'workspace.dashboard.v1.menuItem',
                        programId: 'prog.web.core.dashboard.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 1,
                        title: 'Dashboard v1',
                        titleI18ns: [
                            {
                                langCode: 'en',
                                value: 'Dashboard V1'
                            },
                            {
                                langCode: 'th',
                                value: 'แดชบอร์ด V1'
                            }
                        ],
                        description: 'Dashboard v1',
                        breadcrumbs: [
                            {
                                type: BreadcrumbType.Link,
                                text: 'Dashboard',
                                textI18ns: [
                                    {
                                        langCode: 'en',
                                        value: 'Dashboard'
                                    },
                                    {
                                        langCode: 'th',
                                        value: 'แดชบอร์ด'
                                    }
                                ],
                                href: 'javascript:void(0)'
                            },
                            {
                                type: BreadcrumbType.Text,
                                text: 'V1',
                                textI18ns: [
                                    {
                                        langCode: 'en',
                                        value: 'V1'
                                    },
                                    {
                                        langCode: 'th',
                                        value: 'V1'
                                    }
                                ]
                            }
                        ],
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/dashboard/v1',
                        viewPath: 'ui/views/workspace/dashboard/DashBoardV1View.vue',
                        lazyLoad: true,
                        sideMenuMode: SideBarMenuMode.Collapsed,
                        active: false,
                        children: []
                    },

                    {
                        menuId: 'workspace.dashboard.v2.menuItem',
                        programId: 'prog.web.core.dashboard.v2',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 2,
                        title: 'Dashboard v2',
                        titleI18ns: [
                            {
                                langCode: 'en',
                                value: 'Dashboard V2'
                            },
                            {
                                langCode: 'th',
                                value: 'แดชบอร์ด V2'
                            }
                        ],
                        description: 'Dashboard v2',
                        breadcrumbs: [
                            {
                                type: BreadcrumbType.Link,
                                text: 'Dashboard',
                                textI18ns: [
                                    {
                                        langCode: 'en',
                                        value: 'Dashboard'
                                    },
                                    {
                                        langCode: 'th',
                                        value: 'แดชบอร์ด'
                                    }
                                ],
                                href: 'javascript:void(0)'
                            },
                            {
                                type: BreadcrumbType.Text,
                                text: 'V1',
                                textI18ns: [
                                    {
                                        langCode: 'en',
                                        value: 'V2'
                                    },
                                    {
                                        langCode: 'th',
                                        value: 'V2'
                                    }
                                ]
                            }
                        ],
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/dashboard/v2',
                        viewPath: 'ui/views/workspace/dashboard/DashBoardV2View.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    },

                    {
                        menuId: 'workspace.dashboard.setting.menuDropdown',
                        itemType: ItemType.MenuDropdown,
                        hierachyType: HierachyType.Middle,
                        hierachyLevel: 2,
                        seqNo: 1,
                        title: 'Setting',
                        description: 'Setting',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'fas fa-cogs',
                        action: Action.ToggleDropdown,
                        href: '/workspace/dashboard/setting',
                        dropdownOpen: false,
                        active: false,
                        children: [
                            {
                                menuId: 'workspace.dashboard.setting.v1.menuItem',
                                programId: 'prog.web.core.dashboard.setting.v1',
                                itemType: ItemType.MenuItem,
                                hierachyType: HierachyType.Leaf,
                                hierachyLevel: 3,
                                seqNo: 1,
                                title: 'Setting v1',
                                titleI18ns: [
                                    {
                                        langCode: 'en',
                                        value: 'Setting V1'
                                    },
                                    {
                                        langCode: 'th',
                                        value: 'ตั้งค่า V1'
                                    }
                                ],
                                description: 'Setting v1',
                                breadcrumbs: [
                                    {
                                        type: BreadcrumbType.Link,
                                        text: 'Dashboard',
                                        textI18ns: [
                                            {
                                                langCode: 'en',
                                                value: 'Dashboard'
                                            },
                                            {
                                                langCode: 'th',
                                                value: 'แดชบอร์ด'
                                            }
                                        ],
                                        href: 'javascript:void(0)'
                                    },
                                    {
                                        type: BreadcrumbType.Link,
                                        text: 'Setting',
                                        textI18ns: [
                                            {
                                                langCode: 'en',
                                                value: 'Setting'
                                            },
                                            {
                                                langCode: 'th',
                                                value: 'ตั้งค่า'
                                            }
                                        ],
                                        href: 'javascript:void(0)'
                                    },
                                    {
                                        type: BreadcrumbType.Text,
                                        text: 'V1',
                                        textI18ns: [
                                            {
                                                langCode: 'en',
                                                value: 'V1'
                                            },
                                            {
                                                langCode: 'th',
                                                value: 'V1'
                                            }
                                        ]
                                    }
                                ],
                                iconType: IconType.AwesomeFont,
                                iconFontClass: 'far fa-circle',
                                action: Action.Route,
                                href: '/workspace/dashboard/setting/v1',
                                viewPath: 'ui/views/workspace/dashboard/setting/DashboardSettingV1View.vue',
                                lazyLoad: true,
                                active: false,
                                children: []
                            },
        
                            {
                                menuId: 'workspace.dashboard.setting.v2.menuItem',
                                programId: 'prog.web.core.dashboard.setting.v2',
                                itemType: ItemType.MenuItem,
                                hierachyType: HierachyType.Leaf,
                                hierachyLevel: 3,
                                seqNo: 2,
                                title: 'Setting v2',
                                titleI18ns: [
                                    {
                                        langCode: 'en',
                                        value: 'Setting V2'
                                    },
                                    {
                                        langCode: 'th',
                                        value: 'ตั้งค่า V2'
                                    }
                                ],
                                description: 'Setting v2',
                                breadcrumbs: [
                                    {
                                        type: BreadcrumbType.Link,
                                        text: 'Dashboard',
                                        textI18ns: [
                                            {
                                                langCode: 'en',
                                                value: 'Dashboard'
                                            },
                                            {
                                                langCode: 'th',
                                                value: 'แดชบอร์ด'
                                            }
                                        ],
                                        href: 'javascript:void(0)'
                                    },
                                    {
                                        type: BreadcrumbType.Link,
                                        text: 'Setting',
                                        textI18ns: [
                                            {
                                                langCode: 'en',
                                                value: 'Setting'
                                            },
                                            {
                                                langCode: 'th',
                                                value: 'ตั้งค่า'
                                            }
                                        ],
                                        href: 'javascript:void(0)'
                                    },
                                    {
                                        type: BreadcrumbType.Text,
                                        text: 'V1',
                                        textI18ns: [
                                            {
                                                langCode: 'en',
                                                value: 'V2'
                                            },
                                            {
                                                langCode: 'th',
                                                value: 'V2'
                                            }
                                        ]
                                    }
                                ],
                                iconType: IconType.AwesomeFont,
                                iconFontClass: 'far fa-circle',
                                action: Action.Route,
                                href: '/workspace/dashboard/setting/v2',
                                viewPath: 'ui/views/workspace/dashboard/setting/DashboardSettingV2View.vue',
                                lazyLoad: true,
                                active: false,
                                children: []
                            }
                        ]
                    }
                ]
            }
        ];
    }

    protected buildMockAssetSetupMenus(): Menu[]
    {
        return [
            {
                menuId: 'workspace.fms.acquisition.asset_setup.section',
                itemType: ItemType.Section,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 4,
                title: 'Asset Setup',
                description: '',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-cogs',
                action: Action.None,
                lazyLoad: false,
                active: false,
                children: [
                    {
                        menuId: 'menu.workspace.fms.acquisition.setting_format_sticker.menuItem',
                        programId: 'prog.menu.workspace.fms.acquisition.setting_format_sticker.menuItem.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 1,
                        title: 'Setting Format Sticker',
                        description: 'Setting Format Sticker',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/fms/acquisition/setting-format-sticker',
                        viewPath: 'ui/views/workspace/fms/acquisition/SettingFormatStickerView.vue',
                        lazyLoad: true,
                        active: false
                    }
                ]
            },

            // {
            //     menuId: 'menu.workspace.fms.acquisition.setting_format_sticker.menuItem',
            //     programId: 'prog.menu.workspace.fms.acquisition.setting_format_sticker.menuItem.v1',
            //     itemType: ItemType.MenuItem,
            //     hierachyType: HierachyType.Leaf,
            //     hierachyLevel: 2,
            //     seqNo: 1,
            //     title: 'Setting Format Sticker',
            //     description: 'Setting Format Sticker',
            //     iconType: IconType.AwesomeFont,
            //     iconFontClass: 'far fa-circle',
            //     action: Action.Route,
            //     href: '/workspace/fms/acquisition/setting-format-sticker',
            //     viewPath: 'ui/views/workspace/fms/acquisition/SettingFormatStickerView.vue',
            //     lazyLoad: true,
            //     active: false,
            //     children: []
            // }
        ];
    }

    protected buildMockAssetAcquisitionMenus(): Menu[]
    {
        return [
            {
                menuId: 'workspace.fms.acquisition.section',
                itemType: ItemType.Section,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 4,
                title: 'Asset Acquisition',
                description: '',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-cogs',
                action: Action.None,
                lazyLoad: false,
                active: false,
                children: []
            },

            {
                menuId: 'menu.workspace.fms.acquisition.print_tag_sticker.menuItem',
                programId: 'prog.menu.workspace.fms.acquisition.print_tag_sticker.menuItem.v1',
                itemType: ItemType.MenuItem,
                hierachyType: HierachyType.Leaf,
                hierachyLevel: 2,
                seqNo: 1,
                title: 'Print Tag Sticker',
                description: 'Print Tag Sticker',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'far fa-circle',
                action: Action.Route,
                href: '/workspace/fms/acquisition/print-tag-sticker',
                viewPath: 'ui/views/workspace/fms/acquisition/PrintTagStickerView.vue',
                lazyLoad: true,
                active: false,
                children: []
            }
        ];
    }

    protected buildMockAssetPhysicalCountMenus(): Menu[]
    {
        return [
            {
                menuId: 'workspace.fms.physicalcount.section',
                itemType: ItemType.Section,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 4,
                title: 'Asset Physical Count',
                description: '',
                iconType: IconType.AwesomeFont,
                iconFontClass: '',
                action: Action.None,
                lazyLoad: false,
                active: false,
                children: []
            },

            {
                menuId: 'menu.workspace.fms.physicalcount.countplan.menuItem',
                programId: 'prog.menu.workspace.fms.physicalcount.countplan.menuItem.v1',
                itemType: ItemType.MenuItem,
                hierachyType: HierachyType.Leaf,
                hierachyLevel: 2,
                seqNo: 1,
                title: 'Plan Physical Count',
                description: 'Plan Physical Count',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'far fa-circle',
                action: Action.Route,
                href: '/workspace/fms/physicalcount/countplan',
                viewPath: 'ui/views/workspace/fms/physicalcount/CountPlanView.vue',
                lazyLoad: true,
                active: false,
                children: []
            }
        ];
    }

    protected buildMockTutorialMenus(): Menu[]
    {
        return [
            {
                menuId: 'workspace.tutorial.section',
                itemType: ItemType.Section,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 4,
                title: 'Tutorial',
                description: 'Tutorial',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-ellipsis-v',
                action: Action.None,
                lazyLoad: false,
                active: false,
                children: []
            },

            {
                menuId: 'workspace.tutorial.sample.menuDropdown',
                itemType: ItemType.MenuDropdown,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 5,
                title: 'Sample',
                description: 'Sample',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-folder-open',
                action: Action.ToggleDropdown,
                href: '/workspace/tutorial/sample',
                dropdownOpen: true,
                active: false,
                children: [
                    {
                        menuId: 'menu.workspace.tutorial.sample.input.menuItem',
                        programId: 'prog.menu.workspace.tutorial.sample.input.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 1,
                        title: 'Input Sample',
                        description: 'Input Sample',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/tutorial/sample/input',
                        viewPath: 'ui/views/workspace/tutorial/samples/InputSampleView.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    },

                    {
                        menuId: 'menu.workspace.tutorial.sample.button.menuItem',
                        programId: 'prog.menu.workspace.tutorial.sample.button.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 2,
                        title: 'Button Sample',
                        description: 'Button Sample',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/tutorial/sample/button',
                        viewPath: 'ui/views/workspace/tutorial/samples/ButtonSampleView.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    },

                    {
                        menuId: 'menu.workspace.tutorial.sample.dropdown.menuItem',
                        programId: 'prog.menu.workspace.tutorial.sample.dropdown.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 3,
                        title: 'Dropdown Sample',
                        description: 'Dropdown Sample',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/tutorial/sample/dropdown',
                        viewPath: 'ui/views/workspace/tutorial/samples/DropdownSampleView.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    },

                    {
                        menuId: 'menu.workspace.tutorial.sample.form1.menuItem',
                        programId: 'prog.menu.workspace.tutorial.sample.form1.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 3,
                        title: 'Form Sample 1',
                        description: 'Form Sample 1',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/tutorial/sample/form1',
                        viewPath: 'ui/views/workspace/tutorial/samples/FormSample1View.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    }
                ]
            },

            {
                menuId: 'workspace.tutorial.vueuse.menuDropdown',
                itemType: ItemType.MenuDropdown,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 6,
                title: 'VueUse',
                description: 'VueUse',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-folder-open',
                action: Action.ToggleDropdown,
                href: '/workspace/tutorial/vueuse',
                dropdownOpen: true,
                active: false,
                children: [
                    {
                        menuId: 'menu.workspace.tutorial.vueuse.usemouse.menuItem',
                        programId: 'prog.menu.workspace.tutorial.vueuse.usemouse.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 1,
                        title: 'UseMouse',
                        description: 'UseMouse',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/tutorial/vueuse/usemouse',
                        viewPath: 'ui/views/workspace/tutorial/vueuse/UseMouseSampleView.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    },

                    {
                        menuId: 'menu.workspace.tutorial.vueuse.usenetwork.menuItem',
                        programId: 'prog.menu.workspace.tutorial.vueuse.usenetwork.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 1,
                        title: 'UseNetwork',
                        description: 'UseNetwork',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/tutorial/vueuse/usenetwork',
                        viewPath: 'ui/views/workspace/tutorial/vueuse/UseNetworkSampleView.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    }
                ]
            }
        ];
    }

    // protected buildMockSettingMenus(): Menu[]
    // {
    //     return [
    //         {
    //             menuId: 'workspace.setting.section',
    //             itemType: ItemType.Section,
    //             hierachyType: HierachyType.Root,
    //             hierachyLevel: 1,
    //             seqNo: 4,
    //             title: 'Setting',
    //             description: 'Setting',
    //             iconType: IconType.AwesomeFont,
    //             iconFontClass: 'fas fa-cogs',
    //             action: Action.None,
    //             lazyLoad: false,
    //             active: false,
    //             children: []
    //         },

    //         {
    //             menuId: 'workspace.setting.user_profile',
    //             programId: 'prog.web.core.setting.user_profile',
    //             itemType: ItemType.MenuItem,
    //             hierachyType: HierachyType.Root,
    //             hierachyLevel: 1,
    //             seqNo: 5,
    //             title: 'User Profile',
    //             description: 'User Profile',
    //             iconType: IconType.AwesomeFont,
    //             iconFontClass: 'fas fa-user-cog',
    //             action: Action.Route,
    //             href: '/workspace/setting/user-profile',
    //             viewPath: 'ui/views/workspace/setting/UserProfileView.vue',
    //             lazyLoad: true,
    //             active: false,
    //             children: []
    //         },

    //         {
    //             menuId: 'workspace.setting.change_password',
    //             programId: 'prog.web.core.setting.change_password',
    //             itemType: ItemType.MenuItem,
    //             hierachyType: HierachyType.Root,
    //             hierachyLevel: 1,
    //             seqNo: 6,
    //             title: 'Change Password',
    //             description: 'Change Password',
    //             iconType: IconType.AwesomeFont,
    //             iconFontClass: 'fas fa-key',
    //             action: Action.Route,
    //             href: '/workspace/setting/change-password',
    //             viewPath: 'ui/views/workspace/setting/ChangePasswordView.vue',
    //             lazyLoad: true,
    //             active: false,
    //             children: []
    //         },

    //         {
    //             menuId: 'workspace.setting.user_management',
    //             programId: 'prog.web.core.setting.user_management',
    //             itemType: ItemType.MenuItem,
    //             hierachyType: HierachyType.Root,
    //             hierachyLevel: 1,
    //             seqNo: 7,
    //             title: 'Manage User',
    //             description: 'Manage User',
    //             iconType: IconType.AwesomeFont,
    //             iconFontClass: 'fas fa-users',
    //             action: Action.Route,
    //             href: '/workspace/setting/user/management',
    //             viewPath: 'ui/views/workspace/system/user/UserManageView.vue',
    //             lazyLoad: true,
    //             active: false,
    //             children: []
    //         }
    //     ];
    // }

    protected buildMockSettingMenus(): Menu[]
    {
        return [
            {
                menuId: 'workspace.setting.section',
                itemType: ItemType.Section,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 4,
                title: 'Setting',
                description: 'Setting',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-cogs',
                action: Action.None,
                lazyLoad: false,
                active: false,
                children: []
            },

            {
                menuId: 'workspace.setting.user_profile',
                programId: 'prog.web.core.setting.user_profile',
                itemType: ItemType.MenuItem,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 5,
                title: 'User Profile',
                description: 'User Profile',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-user-cog',
                action: Action.Route,
                href: '/workspace/system/user/user-profile',
                viewPath: 'ui/views/workspace/system/user/UserProfileView.vue',
                lazyLoad: true,
                active: false,
                children: []
            },

            {
                menuId: 'workspace.setting.change_password',
                programId: 'prog.web.core.setting.change_password',
                itemType: ItemType.MenuItem,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 6,
                title: 'Change Password',
                description: 'Change Password',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-key',
                action: Action.Route,
                href: '/workspace/system/user/change-password',
                viewPath: 'ui/views/workspace/system/user/ChangePasswordView.vue',
                lazyLoad: true,
                active: false,
                children: []
            },

            {
                menuId: 'workspace.setting.user_management',
                programId: 'prog.web.core.setting.user_management',
                itemType: ItemType.MenuItem,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 7,
                title: 'Manage User',
                description: 'Manage User',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-users',
                action: Action.Route,
                href: '/workspace/system/user/management',
                viewPath: 'ui/views/workspace/system/user/UserManageView.vue',
                lazyLoad: true,
                active: false,
                children: []
            }
        ];
    }

    protected buildMockFmsReportingMenus(): Menu[]
    {
        return [
            {
                menuId: 'workspace.fms.reporting.section',
                itemType: ItemType.Section,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 4,
                title: 'Reporting',
                description: '',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-cogs',
                action: Action.None,
                lazyLoad: false,
                active: false,
                children: []
            },

            {
                menuId: 'workspace.fms.reporting.menuDropdown',
                itemType: ItemType.MenuDropdown,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 5,
                title: 'Reporting',
                description: 'Reporting',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-folder-open',
                action: Action.ToggleDropdown,
                href: '/workspace/fms/reporting',
                dropdownOpen: true,
                active: false,
                children: [
                    {
                        menuId: 'menu.workspace.fms.reporting.asset_detail.menuItem',
                        programId: 'prog.menu.workspace.fms.reporting.asset_detail.menuItem.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 1,
                        title: '3001 Asset Detail',
                        description: '3001 Asset Detail',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/fms/reporting/asset-detail',
                        viewPath: 'ui/views/workspace/fms/reporting/AssetDetailReportView.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    },

                    {
                        menuId: 'menu.workspace.fms.reporting.asset_detail_without_cost.menuItem',
                        programId: 'prog.menu.workspace.fms.reporting.asset_detail_without_cost.menuItem.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 2,
                        title: '3002 Asset Detail Without Cost',
                        description: '3002 Asset Detail Without Cost',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/fms/reporting/asset-detail-without-cost',
                        viewPath: 'ui/views/workspace/fms/reporting/AssetDetailWithoutCostReportView.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    },

                    {
                        menuId: 'menu.workspace.fms.reporting.asset_value.menuItem',
                        programId: 'prog.menu.workspace.fms.reporting.asset_value.menuItem.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 3,
                        title: '3004 Asset Value',
                        description: '3004 Asset Value',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/fms/reporting/asset-value',
                        viewPath: 'ui/views/workspace/fms/reporting/AssetValueReportView.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    },

                    {
                        menuId: 'menu.workspace.fms.reporting.physicalcount_result.menuItem',
                        programId: 'prog.menu.workspace.fms.reporting.physicalcount_result.menuItem.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 4,
                        title: '4002 Physical count result',
                        description: '4002 Physical count result',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/fms/reporting/physicalcount/result',
                        viewPath: 'ui/views/workspace/fms/reporting/PhysicalCountResultReportView.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    },

                    {
                        menuId: 'menu.workspace.fms.reporting.physicalcount_summary.menuItem',
                        programId: 'prog.menu.workspace.fms.reporting.physicalcount_summary.menuItem.v1',
                        itemType: ItemType.MenuItem,
                        hierachyType: HierachyType.Leaf,
                        hierachyLevel: 2,
                        seqNo: 5,
                        title: '4003 Physical count summary',
                        description: '4003 Physical count summary',
                        iconType: IconType.AwesomeFont,
                        iconFontClass: 'far fa-circle',
                        action: Action.Route,
                        href: '/workspace/fms/reporting/physicalcount/summary',
                        viewPath: 'ui/views/workspace/fms/reporting/PhysicalCountSummaryReportView.vue',
                        lazyLoad: true,
                        active: false,
                        children: []
                    }
                ]
            }
        ];
    }

    protected buildMockImportDataMenus(): Menu[]
    {
        return [
            {
                menuId: 'workspace.core.import_data.section',
                itemType: ItemType.Section,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 4,
                title: 'Import Data',
                description: '',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-cogs',
                action: Action.None,
                lazyLoad: false,
                active: false,
                children: []
            },

            {
                menuId: 'menu.workspace.core.import_data.menuItem',
                programId: 'prog.menu.workspace.core.import_data.menuItem.v1',
                itemType: ItemType.MenuItem,
                hierachyType: HierachyType.Leaf,
                hierachyLevel: 2,
                seqNo: 1,
                title: 'Import Data',
                description: 'Import Data',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'far fa-circle',
                action: Action.Route,
                href: '/workspace/core/import-data',
                viewPath: 'ui/views/workspace/core/import-data/ImportDataView.vue',
                lazyLoad: true,
                active: false,
                children: []
            }
        ];
    }

    protected buildMockSettingSystemMenus(): Menu[]
    {
        return [
            {
                menuId: 'workspace.core.setting_system.section',
                itemType: ItemType.Section,
                hierachyType: HierachyType.Root,
                hierachyLevel: 1,
                seqNo: 4,
                title: 'Setting System',
                description: '',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'fas fa-cogs',
                action: Action.None,
                lazyLoad: false,
                active: false,
                children: []
            },

            {
                menuId: 'menu.workspace.core.notify_template.setup.menuItem',
                programId: 'prog.menu.workspace.core.notify_template.setup.menuItem.v1',
                itemType: ItemType.MenuItem,
                hierachyType: HierachyType.Leaf,
                hierachyLevel: 2,
                seqNo: 1,
                title: 'Notify Template',
                description: 'Notify Template',
                iconType: IconType.AwesomeFont,
                iconFontClass: 'far fa-circle',
                action: Action.Route,
                href: '/workspace/core/notify-template/setup',
                viewPath: 'ui/views/workspace/core/notify-template/NotifyTemplateSetupView.vue',
                lazyLoad: true,
                active: false,
                children: []
            }
        ];
    }
}

const mockMenuService = new MockMenuService();
export default mockMenuService;