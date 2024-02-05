<template>
  <el-form ref="basicInfoForm" :model="info" :rules="rules" label-width="150px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="Table name" prop="tableName">
          <el-input placeholder="Please enter the repository name" v-model="info.tableName" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="Table comment" prop="tableComment">
          <el-input placeholder="Please enter" v-model="info.tableComment" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="Class name" prop="className">
          <el-input placeholder="Please enter" v-model="info.className" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="Author" prop="functionAuthor">
          <el-input placeholder="Please enter" v-model="info.functionAuthor" />
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="Remark" prop="remark">
          <el-input type="textarea" :rows="3" v-model="info.remark"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
export default {
  props: {
    info: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      rules: {
        tableName: [
          { required: true, message: "Please enter the table name", trigger: "blur" }
        ],
        tableComment: [
          { required: true, message: "Please enter the table description", trigger: "blur" }
        ],
        className: [
          { required: true, message: "Please enter the entity class name", trigger: "blur" }
        ],
        functionAuthor: [
          { required: true, message: "Please enter the author", trigger: "blur" }
        ]
      }
    };
  }
};
</script>
