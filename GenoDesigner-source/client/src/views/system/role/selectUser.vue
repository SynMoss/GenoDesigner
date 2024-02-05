<template>
     <el-dialog title="Select Users" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="UserName" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="Please Enter UserName"
          clearable
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
          @blur="queryParams.userName = $event.target.value.trim()"
        />
      </el-form-item>
      <el-form-item label="PhoneNumber" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          placeholder="Please Enter PhoneNumber"
          clearable
          @keyup.enter.native="handleQuery"
          @clear="handleQuery"
          @blur="queryParams.phonenumber = $event.target.value.trim()"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="userList" @selection-change="handleSelectionChange" height="260px">
        <el-table-column type="selection" :selectable="selectable" width="55"></el-table-column>
        <el-table-column label="UserName" prop="userName" :show-overflow-tooltip="true" />
        <el-table-column label="NickName" prop="nickName" :show-overflow-tooltip="true" />
        <el-table-column label="Email" prop="email" :show-overflow-tooltip="true" />
        <el-table-column label="PhoneNumber" prop="phonenumber" :show-overflow-tooltip="true" />
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
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectUser">OK</el-button>
      <el-button @click="visible = false">Cancel</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { unallocatedUserList, authUserSelectAll } from "@/api/system/role";
export default {
  dicts: ['sys_normal_disable'],
  props: {

    roleId: {
      type: [Number, String]
    }
  },
  data() {
    return {
     
      visible: false,
      
      userIds: [],
      
      total: 0,
     
      userList: [],
      
      unchangeable:['admin'],
      
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: undefined,
        userName: '',
        phonenumber: ''
      }
    };
  },
  methods: {
    
    show() {
      this.queryParams.roleId = this.roleId;
      this.getList();
      this.visible = true;
    },
    selectable(row, index) {
      return this.unchangeable.indexOf(row.userName) <= -1
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
   
    handleSelectionChange(selection) {
      this.userIds = selection.map(item => item.userId);
    },
    
    getList() {
      unallocatedUserList(this.queryParams).then(res => {
        this.userList = res.rows;
        this.total = res.total;
      });
    },
    
    handleQuery() {
      this.queryParams.userName = this.queryParams.userName.trim()
      this.queryParams.phonenumber = this.queryParams.phonenumber.trim()
      this.getList();
    },
  
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    
    handleSelectUser() {
      const roleId = this.queryParams.roleId;
      const userIds = this.userIds.join(",");
      if (userIds == "") {
        this.$modal.msgError("Please Select The User You Want To Assign");
        return;
      }
      authUserSelectAll({ roleId: roleId, userIds: userIds }).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
        }
      });
    }
  }
};
</script>
