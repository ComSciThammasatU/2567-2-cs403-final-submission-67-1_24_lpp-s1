<template>
    <div>
        <h1>Home</h1>
        <i class="fa fa-edit"></i>
        <!-- <select
              class="form-control select2 select2-danger"
              data-dropdown-css-class="select2-danger"
              style="width: 100%"
            >
            <option selected>Alabama</option>
            <option>Alaska</option>
            <option>California</option>
            <option>Delaware</option>
            <option>Tennessee</option>
            <option>Texas</option>
            <option>Washington</option>
        </select> -->

        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <select class="form-control select2 select2-danger" 
                            data-dropdown-css-class="select2-danger" 
                            style="width: 100%">
                        <option selected>Alabama</option>
                        <option>Alaska</option>
                        <option>California</option>
                        <option>Delaware</option>
                        <option>Tennessee</option>
                        <option>Texas</option>
                        <option>Washington</option>
                    </select>

                    <select id="dropdown" class="form-control select2 select2-danger" 
                            data-dropdown-css-class="select2-danger" 
                            style="width: 100%" 
                            v-model="state.selectedItems"
                            multiple>
                        <template v-for="(item) in state.items" :key="item.id">
                            <option :value="item.id">{{ item.title }}</option>
                        </template>
                    </select>
                </div>

                <div class="col-md-6">
                    <input type="text" class="form-control" v-model="state.text" />
                    <button class="btn btn-primary" @click="addItem()">Add</button>
                </div>

                <div>
                    selectedItem: {{ state.selectedItem }}
                    <br>
                    selectedItems: {{ state.selectedItems }}
                    <br>
                    items: {{ state.items }}
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { onMounted, reactive } from 'vue';

    interface Item {
        id: string;
        title: string;
    };

    // const dropdown = ref(null);

    const emit = defineEmits(['change', 'update', 'update:modelValue']);

    const state = reactive({
        text: '',
        selectedItem: '',
        selectedItems: [] as string[],
        items: [
            {
                id: '001',
                title: 'Alabama'
            },
            {
                id: '002',
                title: 'Alaska'
            },
            {
                id: '003',
                title: 'California'
            },
            {
                id: '004',
                title: 'Delaware'
            },
            {
                id: '005',
                title: 'Tennessee'
            }
        ] as Item[]
    });

    // state.selectedItems.push(state.items[1].id);
    // state.selectedItems.push(state.items[3].id);

    onMounted(() => {
        initSelect2();
        

        // dropdown.value?.addEventListener('change', () => {
        //     console.log("dropdown.change");
        // });

        // const dropdown = document.getElementById("dropdown") as HTMLElement;
        // console.log(dropdown);
        // dropdown.addEventListener("change", (event) => {
        //     console.log("dropdown.change");
        //     initSelect2();
        // });

        $("#dropdown").on("change", (event) => {
            console.log("dropdown.change");
            console.log(event);
            // const event = new Event('change', { bubbles: true, cancelable: true });
            const event2 = new Event('change', { bubbles: true, cancelable: true });
            // var event2 = new CustomEvent('update:modelValue', {
            //     bubbles: true
            // });
            dispatchEvent(event2);

            console.log($("#dropdown").val());
            emit("update:modelValue", $("#dropdown").val());
            state.selectedItems = $("#dropdown").val() as string[];

            // state.selectedItems = [];
            const options: HTMLCollection = event.currentTarget.children;
            
            for(let i=0; i<options.length; i++) {
                // state.selectedItems.push(options.item(i)?._value);
            }
            // event.currentTarget.children.forEach(option => {
            //     state.selectedItems.push(option.value);
            // });
            // initSelect2();
        });
    });

    function initSelect2()
    {
        console.log("!!! initSelect2 !!!");
        $(".select2").select2();
    }

    function addItem()
    {
        state.items.push({
            id: new Date().getMilliseconds().toString(),
            title: state.text
        });

        state.text = '';
    }
</script>