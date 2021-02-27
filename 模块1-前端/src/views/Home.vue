<template>
  <d2-container>
    <el-form ref="form" :model="formData" label-width="120px">
      <el-row>
        <el-col :span="10">
          <el-form-item label="文件"  prop="mediaFileUrl">
            <el-upload style="width: 100%;"
                       class="upload-demo"
                       ref="uploadMul"
                       multiple
                       action
                       drag
                       :limit="maxUploadSize"
                       :on-exceed="uploadLimit"
                       :http-request="uploadFile"
                       :file-list="fileList"
                       :auto-upload="false"
                       :on-remove="handleRemove"
                       :before-upload="beforeUpload"
                       :on-change="uploadChange"
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">支持上传jpg/png/gif文件，且不超过100M</div>
            </el-upload>
            <div v-show="progressFlag" class="head-img">
              <el-progress :text-inside="true" :stroke-width="14" :percentage="progressPercent" status="success"></el-progress>
            </div>
            <el-button size="mini" type="success" @click="submitUpload">上传到服务器</el-button>
            <el-button v-if="this.fileList.length > 0" size="mini" type="warning" @click="clearFiles">清空</el-button>
          </el-form-item>
        </el-col>
        <el-col :offset="4"></el-col>
      </el-row>
    </el-form>
  </d2-container>
</template>
<script>
  import axios from 'axios'
  export default {
    data () {
      return {
        maxUploadSize: 10,
        progressFlag: false,
        progressPercent: 10,
        innerVisible: false,
        fileList: [],
        isViewDisabled: false,
        formData: {},
        param: {} // 上传文件主要参数
      }
    },
    methods: {
      submitUpload () {
        if (this.fileList.length < 1) {
          this.$message.warning('请选择文件!')
          return false
        }
        this.$refs.uploadMul.submit()
        if (this.param instanceof FormData) {
          // 附加参数
          this.param.append('orderid', 111)
          let that = this
          that.progressFlag = true
          axios({
            url: 'http://localhost:8082/file/upload',
            method: 'post',
            data: that.param,
            headers: { 'Content-Type': 'multipart/form-data' },
            onUploadProgress: progressEvent => {
              // progressEvent.loaded:已上传文件大小
              // progressEvent.total:被上传文件的总大小
              // 进度条
              that.progressPercent = ((progressEvent.loaded / progressEvent.total) * 100).toFixed(0) | 0
            }
          }).then(res => {
            this.param = {}
            this.fileList = []
            console.log(res)
            if (res.data.status === 200 && that.progressPercent === 100) {
              setTimeout(function () {
                that.$message({
                  message: '上传成功！',
                  type: 'success',
                  duration: '2000'
                })
                that.progressFlag = false
                that.progressPercent = 0
                that.$refs.uploadMul.clearFiles()
              }, 1000)
              let result = res.data.body.data
              console.log(result)
            } else {
              setTimeout(function () {
                that.$message({
                  message: res.data.msg,
                  type: 'error',
                  duration: '2000'
                })
                that.progressFlag = false
                that.progressPercent = 0
                that.$refs.uploadMul.clearFiles()
              }, 1000)
            }
          }).catch(() => {
            that.progressFlag = false
            that.progressPercent = 0
            that.$refs.uploadMul.clearFiles()
            that.$message({
              message: '上传失败！',
              type: 'error',
              duration: '2000'
            })
          })
        } else {
          console.log(this.param instanceof FormData)
        }
      },
      handleRemove (file, fileList) {
        this.$message.warning(`已移除文件:  ${file.name}!`)
        // 每移除一个文件,param重新赋值
        this.param = new FormData()
        this.fileList = [...fileList]
        this.fileList.forEach((file, index) => {
          this.param.append(`file`, file.raw) // 把单个文件重命名，存储起来（给后台）
        })
      },
      uploadChange (file, fileList) {
        this.param = new FormData()
        this.fileList = [...fileList]
        this.fileList.forEach((file, index) => {
          this.param.append(`file`, file.raw) // 把单个文件重命名，存储起来（给后台）
        })
      },
      // 超出上传个数时调用
      uploadLimit (files, fileList) {
        this.$message.error(`最多允许同时上传${this.maxUploadSize}个文件!`)
      },
      beforeUpload (file) {
      },
      uploadFile (file) {
        // 该方法需存在,防止空action时element-ui报404异常
      },
      clearFiles () {
        this.fileList = []
        this.param = {}
        this.$refs.uploadMul.clearFiles()
      },
      // 初始化表单数据
      init () {
      }
    }
  }
</script>
<style lang="scss" scoped>
</style>
