<template>
    <div class="home">
        <a-button type="primary" @click="() => openAddModal('增加联系人')" style="margin-bottom: 8px">增加联系人</a-button>
        <!-- <a-button type="primary" @click="() => openAddModal" style="margin-bottom: 8px">导入联系人</a-button> -->
        <a-button type="primary" @click="() => erportExcel()" style="margin-bottom: 8px">导出联系人</a-button>
        <a-upload-dragger
    v-model:fileList="fileList"
    name="file"
    :multiple="true"
    action="http://localhost:8080/excel"
    @change="handleChange"
    @drop="handleDrop"
  >
    <p class="ant-upload-drag-icon">
      <inbox-outlined></inbox-outlined>
    </p>
    <p class="ant-upload-text">点击或者拖拽文件上传</p>
    <p class="ant-upload-hint">
      导入联系人
    </p>
  </a-upload-dragger>


        <a-table bordered :data-source="dataSource" :columns="columns" :pagination="false">
            <template #bodyCell="{ text, column, record }">
                <div v-if="column.dataIndex === 'type'">
                    {{ text == 1 ? '一般' : text == 2 ? '重要' : '特别重要' }}
                </div>
                <div v-if="column.dataIndex === 'alabel'">
                    {{ text == 0 ? '收藏联系人' :  '书签联系人' }}
                </div>

                <div v-if="column.dataIndex === 'operation'">
                    <a-button type="primary" danger @click="() => handleDelete(record)" style="margin-right: 8px">删除</a-button>

                    <a-button type="primary" @click="() => openAddModal('编辑联系人', record)">编辑</a-button>
                </div>
            </template>
        </a-table>

        <a-modal v-model:open="modalVisible" :footer="null" :title="modalText" centered>
            <a-form :model="addForm" layout="vertical" :rules="addRules" @finish="handleAddFinish">
                <div class="form">
                    <a-form-item :label="item.title" v-for="item in handleTitle" :key="item.dataIndex" :name="item.dataIndex">
                        <a-input v-model:value="addForm[item.dataIndex]" :type="item.title == '联系方式' ? 'number' : 'text'" v-if="item.title != '类型' && item.title != '标签'" />

                        <a-select v-model:value="addForm[item.dataIndex]" v-if="item.title == '类型'">
                            <a-select-option value="1">一般</a-select-option>
                            <a-select-option value="2">重要</a-select-option>
                            <a-select-option value="3">特别重要</a-select-option>
                        </a-select>
                        <a-select v-model:value="addForm[item.dataIndex]" v-if="item.title == '标签'">
                            <a-select-option value="0">收藏联系人</a-select-option>
                            <a-select-option value="1">书签联系人</a-select-option>
                        </a-select>
                    </a-form-item>
                </div>

                <a-form-item>
                    <div style="display: flex; justify-content: flex-end">
                        <a-button type="primary" htmlType="submit" :loading="btnLoading">{{modalText}}</a-button>
                        <a-button style="margin-left: 10px" @click="Cancel">取消</a-button>
                    </div>
                </a-form-item>
            </a-form>
        </a-modal>
    </div>
</template>

<script setup>
    import { computed, onMounted, ref } from 'vue';
    import dayjs from 'dayjs';
    import request from '@/request';

    const columns = [
        {
            title: '序号',
            dataIndex: 'id',
        },
        {
            title: '姓名',
            dataIndex: 'name',
        },
        {
            title: '年龄',
            dataIndex: 'age',
        },
        {
            title: '类型',
            dataIndex: 'type',
        },
        {
            title: '性别',
            dataIndex: 'sex',
        },
        {
            title: '联系方式',
            dataIndex: 'phone',
        },
        {
            title: '地址',
            dataIndex: 'address',
        },
        {
            title: '添加时间',
            dataIndex: 'time',
        },
        {
            title: '邮箱',
            dataIndex: 'email',
        },
        {
            title: '社交媒体账号',
            dataIndex: 'socialMedia',
        },
        {
            title: '标签',
            dataIndex: 'alabel',
        },
        {
            title: '操作',
            dataIndex: 'operation',
        },
    ];

    const addRules = {
        name: { required: true, message: '请输入姓名', trigger: 'blur' },
        phone: [
            { required: true, message: '请输入联系方式', trigger: 'blur' },
            { max: 11, message: '手机号不能超过11位', trigger: 'blur' },
            { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' },
        ],
    };

    const excludeTitle = ['添加时间', '序号', '操作'];

    const handleTitle = computed(() => {
        return columns.filter((item) => !excludeTitle.includes(item.title));
    });

    const modalVisible = ref(false);

    const btnLoading = ref(false);

    const modalText = ref('');

    const addForm = ref({
        type: '1',
    });

    

    const openAddModal = (title, data) => {
        modalText.value = title;

        if (title == '编辑联系人') {
            addForm.value = { ...data };
        }

        modalVisible.value = true;
    };

    const dataSource = ref([]);

    const Cancel = () => {
        modalVisible.value = false;
    };

    const handleAddFinish = async () => {
        btnLoading.value = true;

        // 获取当前时间戳
        const time = Date.now();

        if (modalText.value == '编辑联系人') {
            await request.post(``, addForm.value );
        } else {
            await request.post('', addForm.value );
        }

        btnLoading.value = false;

        modalVisible.value = false;

        addForm.value = { type: '1' };

        getBook();
    };

    const handleDelete = async (record) => {
        await request.post(`${record.id}`);

        getBook();
    };

    const getBook = async () => {
        const { data } = await request.get('http://localhost:8080');

        dataSource.value = data.data;
    };

    const erportExcel = async () => {
        const { data } = await request.post('http://localhost:8080/exportToExcel');
        // window.open('http://localhost:8080/'+data.data);
        alert("导出成功!");
    };

    const fileList = ref([]);
    const handleChange = info => {
    const status = info.file.status;
    if (status !== 'uploading') {
        console.log(info.file, info.fileList);
    }
    if (status === 'done') {
        message.success(`${info.file.name} file uploaded successfully.`);
    } else if (status === 'error') {
        message.error(`${info.file.name} file upload failed.`);
    }
    getBook();
    };
    function handleDrop(e) {
        getBook();
    }


    onMounted(() => {
        getBook();
    });
</script>

<style lang="scss" scoped>
    .home {
        max-width: 1440px;
        margin: 0 auto;
        padding: 20px;
    }

    .form {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 20px;
    }
</style>
