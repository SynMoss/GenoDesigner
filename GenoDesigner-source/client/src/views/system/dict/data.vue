<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="DictType" prop="dictType">
        <el-select v-model="queryParams.dictType">
          <el-option
            v-for="item in typeOptions"
            :key="item.dictId"
            :label="item.dictName"
            :value="item.dictType"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="DictLabel" prop="dictLabel">
        <el-input
          v-model="queryParams.dictLabel"
          placeholder="Please Enter DictLabel"
          @blur="queryParams.dictLabel=$event.target.value.trim()"
          clearable
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select v-model="queryParams.status" placeholder="Data Status" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery" style="font-size: 14px;padding: 10px 20px;">Search</el-button>
        <el-button @click="resetQuery" style="font-size: 14px;padding: 10px 20px;">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          @click="handleAdd"
          v-hasPermi="['system:dict:add']"
        >New</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:dict:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          @click="handleExport"
          v-hasPermi="['system:dict:export']"
        >Export</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          @click="handleClose"
        >Close</el-button>
      </el-col>
    </el-row>
    <div class="table-f">
    <vxe-table
      ref="xTable1"
      :data="dataList"
      auto-resize
      v-loading="loading"
      height="100%"
      border="inner"
      :row-config="{isHover: true}"
      @checkbox-all="selectChangeEvent"
      show-overflow
      @checkbox-change="selectChangeEvent"
      :seq-config="{startIndex:(this.tablePage.currentPage-1) * this.tablePage.pageSize}">
      <vxe-column type="checkbox" min-width="50px" fixed="left"></vxe-column>
      <vxe-column type="seq" title="Seq" min-width="50px" fixed="left"></vxe-column>
      <vxe-column field="dictLabel" title="DictLabel" min-width="100px" fixed="left" show-overflow="tooltip">
        <template slot-scope="scope">
          <span v-if="scope.row.listClass == '' || scope.row.listClass == 'default'">{{scope.row.dictLabel}}</span>
          <el-tag v-else :type="scope.row.listClass == 'primary' ? '' : scope.row.listClass">{{scope.row.dictLabel}}</el-tag>
        </template>
      </vxe-column>
      <vxe-column field="dictValue" title="DictValue" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="dictSort" title="DictSort" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="status" title="Status"  min-width="100px" show-overflow="tooltip">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </vxe-column>
      <vxe-column field="remark" title="Remark" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="createTime" title="CreateTime" min-width="100px" show-overflow="tooltip"></vxe-column>
      <vxe-column field="j" title="Operate" min-width="150px" fixed="right">
      <template slot-scope="scope">
        <a @click="handleUpdate(scope.row)" type="text" size="small" class="table-opera" v-hasPermi="['system:dict:edit']"
          >Edit</a
        >
        <a @click="handleDelete1(scope.row)" type="text" size="small" class="table-opera" v-hasPermi="['system:dict:remove']"
          >Delete</a
        >
      </template>
    </vxe-column>
    </vxe-table>
    </div>
    <div class="pager-flex">
      <div class="pager-flex-total"></div>
      <vxe-pager
        class-name="pager-self"
        background
        size="small"
        :loading="loading"
        :current-page="tablePage.currentPage"
        :page-size="tablePage.pageSize"
        :total="tablePage.totalResult"
        :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'Sizes', 'FullJump']"
        @page-change="pageChangeEvent">
      </vxe-pager>
    </div>

         <el-dialog :title="title" :close-on-click-modal="false"  :visible.sync="open" width="500px" append-to-body>
      <el-form label-position="top" size="small" ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="DictType">
          <el-input v-model="form.dictType" :disabled="true" />
        </el-form-item>
        <el-form-item label="DictLabel" prop="dictLabel">
          <el-input v-model="form.dictLabel" placeholder="Please Enter DictLabel" />
        </el-form-item>
        <el-form-item label="DictValue" prop="dictValue">
          <el-input v-model="form.dictValue" placeholder="Please Enter DictValue" />
        </el-form-item>
        <el-form-item label="CssClass" prop="cssClass">
          <el-input v-model="form.cssClass" placeholder="Please Enter CssClass" />
        </el-form-item>
        <el-form-item label="DictSort" prop="dictSort">
          <el-input-number v-model="form.dictSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="ListClass" prop="listClass">
          <el-select v-model="form.listClass">
            <el-option
              v-for="item in listClassOptions"
              :key="item.value"
              :label="item.label + '(' + item.value + ')'"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Status" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input
          v-model="form.remark"
          type="textarea"
          placeholder="Please Enter Content"
          resize="none"
          maxlength="256"
          show-word-limit
          :rows="6"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">OK</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listData, getData, delData, addData, updateData } from "@/api/system/dict/data";
import { optionselect as getDictOptionselect, getType } from "@/api/system/dict/type";

export default {
  name: "Data",
  dicts: ['sys_normal_disable'],
  data() {
    return {

      loading: true,

      ids: [],

      single: true,

      multiple: true,

      showSearch: true,

      total: 0,

      dataList: [],

      defaultDictType: "",

      title: "",

      open: false,

      listClassOptions: [
        {
          value: "default",
          label: "Default"
        },
        {
          value: "primary",
          label: "Primary"
        },
        {
          value: "success",
          label: "Success"
        },
        {
          value: "info",
          label: "Info"
        },
        {
          value: "warning",
          label: "Warning"
        },
        {
          value: "danger",
          label: "Danger"
        }
      ],

      typeOptions: [],

      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dictName: undefined,
        dictType: undefined,
        status: undefined
      },

      form: {},

      rules: {
        dictLabel: [
          { required: true, message: "DictLabel Cannot Be Empty", trigger: "blur" }
        ],
        dictValue: [
          { required: true, message: "DictValue Cannot Be Empty", trigger: "blur" }
        ],
        dictSort: [
          { required: true, message: "DictSort Cannot Be Empty", trigger: ["blur", "change"] }
        ]
      },
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 100
      }
    };
  },
  created() {
    const dictId = this.$route.params && this.$route.params.dictId;
    this.getType(dictId);
    this.getTypeList();
  },
  methods: {

    getType(dictId) {
      getType(dictId).then(response => {
        this.queryParams.dictType = response.data.dictType;
        this.defaultDictType = response.data.dictType;
        this.getList();
      });
    },

    getTypeList() {
      getDictOptionselect().then(response => {
        this.typeOptions = response.data;
      });
    },

    getList() {
      this.queryParams.pageNum = this.tablePage.currentPage
      this.queryParams.pageSize = this.tablePage.pageSize
      this.loading = true;
      listData(this.queryParams).then(response => {
        this.dataList = response.rows;
        this.tablePage.totalResult = response.total
        this.loading = false;
      });
    },

    cancel() {
      this.open = false;
      this.reset();
    },

    reset() {
      this.form = {
        dictCode: undefined,
        dictLabel: undefined,
        dictValue: undefined,
        cssClass: undefined,
        listClass: 'default',
        dictSort: 0,
        status: "0",
        remark: undefined
      };
      this.resetForm("form");
    },

    handleQuery() {
      this.tablePage.currentPage = 1
      this.getList();
    },

    handleClose() {
      const obj = { path: "/system/dict" };
      this.$tab.closeOpenPage(obj);
    },

    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.dictType = this.defaultDictType;
      this.handleQuery();
    },

    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add DictType";
      this.form.dictType = this.queryParams.dictType;
    },

    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.dictCode)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },

    handleUpdate(row) {
      this.reset();
      const dictCode = row.dictCode || this.ids
      getData(dictCode).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Edit DictType";
      });
    },

    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.dictCode != undefined) {
            updateData(this.form).then(response => {
              this.$store.dispatch('dict/removeDict', this.queryParams.dictType);
              this.$modal.msgSuccess("Edit Success");
              this.open = false;
              this.getList();
            });
          } else {
            addData(this.form).then(response => {
              this.$store.dispatch('dict/removeDict', this.queryParams.dictType);
              this.$modal.msgSuccess("Add Success");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    handleDelete(row) {
      let dictCodes=this.ids;
      this.$modal.confirm('Check Whether To Confirm That The Deletion DictLabel Is Set To"' + this.lables + '"Data Items？').then(function() {
        return delData(dictCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Success");
        this.$store.dispatch('dict/removeDict', this.queryParams.dictType);
      }).catch(() => {});
    },
    handleDelete1(row) {
      let dictCodes=row.dictCode;
      this.$modal.confirm('Check Whether To Confirm That The DictLabel Is Set To"' + row.dictLabel + '"Data Items？').then(function() {
        return delData(dictCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Success");
        this.$store.dispatch('dict/removeDict', this.queryParams.dictType);
      }).catch(() => {});
    },

    handleExport() {
      this.download('system/dict/data/export', {
        ...this.queryParams
      }, `data_${new Date().getTime()}.xlsx`)
    },
    selectChangeEvent ({ checked }) {
      const records = this.$refs.xTable1.getCheckboxRecords()
      let arr=[]
      let arr1=[]
      for(let item of records){
        arr.push(item.dictCode)
        arr1.push(item.dictLabel)
      }
      this.ids=arr
      this.lables=arr1

      if (records && records.length) {
          this.multiple = false;
      } else{
          this.multiple = true;
      }
    },
    getSelectEvent () {
      let selectRecords = this.$refs.xTable1.getCheckboxRecords()
      VXETable.modal.alert(selectRecords.length)
    },

    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.getList()
    },
  }
};
</script>
<style lang="scss" scoped>
  .table-f{
    height: calc(90vh - 250px);
  }
</style>
