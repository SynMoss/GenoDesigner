<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="PostCode" prop="postCode">
        <el-input
          v-model="queryParams.postCode"
          placeholder="Please Enter PostCode"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="PostName" prop="postName">
        <el-input
          v-model="queryParams.postName"
          placeholder="Please Enter PostName"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select v-model="queryParams.status" placeholder="Status" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">Search</el-button>
        <el-button @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          @click="handleAdd"
          v-hasPermi="['system:post:add']"
        >New</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:post:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          @click="handleExport"
          v-hasPermi="['system:post:export']"
        >Export</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="PostId" align="center" prop="postId" >
             <template slot-scope="scope">
                <span>{{scope.$index+1}}</span>
              </template>
            </el-table-column>
      <el-table-column label="PostCode" align="center" prop="postCode" />
      <el-table-column label="PostName" align="center" prop="postName" />
      <el-table-column label="PostSort" align="center" prop="postSort" />
      <el-table-column label="Status" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="CreateTime" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operate" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:post:edit']"
          >Edit</el-button>
          <el-button
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:post:remove']"
          >Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

         <el-dialog :close-on-click-modal="false"  :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form  label-position="top" size="small" v-loading="getLoading" ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="PostName" prop="postName">
          <el-input v-model="form.postName" placeholder="Please Enter PostName" />
        </el-form-item>
        <el-form-item label="PostCode" prop="postCode">
          <el-input v-model="form.postCode" placeholder="Please Enter PostCode" />
        </el-form-item>
        <el-form-item label="PostSort" prop="postSort">
          <el-input-number v-model="form.postSort" controls-position="right" :min="0" />
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
          <el-input v-model="form.remark" type="textarea" placeholder="Please Enter Content" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :disabled="subLoading" :loading="subLoading">OK</el-button>
        <el-button @click="cancel" :disabled="subLoading" :loading="subLoading">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPost, getPost, delPost, addPost, updatePost } from "@/api/system/post";

export default {
  name: "Post",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      getLoading: false,
      subLoading: false,
      
      loading: true,
     
      ids: [],
     
      single: true,
      
      multiple: true,
     
      showSearch: true,
    
      total: 0,
      
      postList: [],
      
      title: "",
     
      open: false,
    
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        postCode: undefined,
        postName: undefined,
        status: undefined
      },
      
      form: {},
      
      rules: {
        postName: [
          { required: true, message: "PostName Cannot Be Empty", trigger: "blur" }
        ],
        postCode: [
          { required: true, message: "PostCode Cannot Be Empty", trigger: "blur" }
        ],
        postSort: [
          { required: true, message: "PostSort Cannot Be Empty", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
   
    getList() {
      this.loading = true;
      listPost(this.queryParams).then(response => {
        this.postList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
   
    cancel() {
      this.open = false;
      this.reset();
    },
    
    reset() {
      this.form = {
        postId: undefined,
        postCode: undefined,
        postName: undefined,
        postSort: 0,
        status: "0",
        remark: undefined
      };
      this.resetForm("form");
    },
    
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.postId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add Post";
    },
  
    handleUpdate(row) {
      this.reset();
      const postId = row.postId
      this.getLoading = true
      this.open = true;
      getPost(postId).then(response => {
        this.form = response.data;
        this.title = "Edit Post";
      }).finally(()=>{
        this.getLoading = false
      });
    },
  
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.subLoading = true
          if (this.form.postId) {
            updatePost(this.form).then(response => {
              this.$modal.msgSuccess("Edit Success");
              this.open = false;
              this.getList();
            }).finally(()=>{
              this.subLoading = false
            });
          } else {
            addPost(this.form).then(response => {
              this.$modal.msgSuccess("Add Success");
              this.open = false;
              this.getList();
            }).finally(()=>{
              this.subLoading = false
            });
          }
        }
      });
    },
   
    handleDelete(row) {
      const postIds = row.postId || this.ids;
      this.$modal.confirm('Whether To Confirm The Deletion Of The Post Number"' + postIds + '"Data Items').then(function() {
        return delPost(postIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Delete Success");
      }).catch(() => {});
    },
    
    handleExport() {
      this.download('system/post/export', {
        ...this.queryParams
      }, `post_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>