import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import vueJsx from "@vitejs/plugin-vue-jsx";
import dynamicImport from 'vite-plugin-dynamic-import';
import * as path from 'path';

console.log("init vite.config");
console.log(`NODE_ENV = ${process.env.NODE_ENV}`);
console.log(`BASE_URL = ${process.env.BASE_URL}`);
console.log(`SERVER_ENDPOINT = ${process.env.SERVER_ENDPOINT}`);

// https://vitejs.dev/config/
export default defineConfig({
	envDir: "./env",

	plugins: [
		vue(),
		vueJsx(),
		dynamicImport()
	],

	resolve: {
		alias: {
			'@': path.resolve(__dirname, './src')
		}
	}
	// base: process.env.BASE_URL.trim()
});