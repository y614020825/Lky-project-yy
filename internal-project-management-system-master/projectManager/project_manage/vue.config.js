// vue.config.js
const webpack = require('webpack')
module.exports = {
    // 更改路径别名
    configureWebpack: {
        plugins: [
          new webpack.ProvidePlugin({
                $:"jquery",
                jQuery:"jquery",
                "windows.jQuery":"jquery"
            })
        ],
        resolve: {
          alias: {
            'assets': '@/assets',
            'components': '@/components',
            'views': '@/views',
            'common': '@/common',
            'network':'@/network',
            'util':'@/util'
          }
        },
        //警告 webpack 的性能提示
        performance: {
            hints:'warning',
            //入口起点的最大体积 整数类型（以字节为单位）
            maxEntrypointSize: 50000000,
            //生成文件的最大体积 整数类型（以字节为单位 300k）
            maxAssetSize: 30000000,
            //只给出 js 文件的性能提示
            assetFilter: function(assetFilename) {
                return assetFilename.endsWith('.js');
            }
        }
      },
      runtimeCompiler: true,
      publicPath: './', //输出的根路径  默认是/ 如果你的网站是app.com/vue 这更改此配置项
      outputDir:'dist',
      assetsDir:'static',
      lintOnSave: false // eslint 不限制
  }