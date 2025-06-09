[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/w8H8oomW)
**<ins>Note</ins>: Students must update this `README.md` file to be an installation manual or a README file for their own CS403 projects.**

**รหัสโครงงาน:** 67-1_24_lpp-s1

**ชื่อโครงงาน (ไทย):** ระบบแบบสำรวจความคิดเห็นเพื่อการประกันคุณภาพการศึกษา ภายในสาขาวิชาวิทยาการคอมพิวเตอร์ มหาวิทยาลัยธรรมศาสตร์

**Project Title (Eng):** Opinion Survey System for Educational Quality Assurance in the Computer Science Department, Thammasat University

**อาจารย์ที่ปรึกษาโครงงาน:** ผศ. ดร.ลัมพาพรรณ พันธ์ชูจิตร์

**ผู้จัดทำโครงงาน:**
1. นางสาวฟิรดาวส์ มะนอ  6309651005  firdaus.man@dome.tu.ac.th
   
Manual / Instructions for your projects starts here !
# Prerequisites
**Objective :**
> To set up a development environment and deploy a multi-part application that integrates with the LINE platform.

**Key Tools/Technologies :**
* IDEs/Editors: Visual Studio Code, SpringToolSuite
* Backend: Java, Spring Boot, Maven
* Frontend: Node.js, npm, Git
* Database: MySQLWorkbench
* Networking/Deployment: Cloudflare 
* LINE Platform: LINE Developers, LINE Official Account Manager

**Workflow Outline:**
1. Install necessary software and tools (Java, Node, Maven, MySQLWorkbench, VS Code, STS, Cloudflare).
2. Set up the LINE platform components (Channel, LIFF apps, Rich Menus) on LINE Developers and LINE Official Account Manager.
3. Create the local project folder named TU SURVEYOR.
4. Run database script on MySQLWorkbench.

**Terminal**
```
brew install maven 
brew install node
npm install
brew install cloudflared
cloudflared tunnel login
```

**SpringToolSuite**
1. Import maven project.
2. Run the project as Spring Boot App for the first time.

**Terminal**
```
cloudflared tunnel --url http:localhost:9000
```

**Visual Studio Code**
1. Open terminal in surveyor-frontend-admin folder.
```
git clone https://github.com/ComSciThammasatU/2567-2-cs403-final-submission-67-1_24_lpp-s1.git
```
2. Copy public URL form terminal (by running  cloudflared tunnel  --url http:localhost:9000 command.). then, paste it on env file (named .env.develop_dev) for config in VITE_SERVER_ENDPOINT line and also config liff id of TU Surveyor Register on VITE_LIFF_ID line.
```
npm run start:dev
```
3. Open another terminal.
```
cloudflared tunnel --url http:localhost:3013
```
4. Copy public URL and paste it on Callback URL in LINE Login and other two LIFF on Endpoint URL.
5. Open terminal in surveyor-frontend-form folder.
```
git clone https://github.com/ComSciThammasatU/2567-2-cs403-final-submission-67-1_24_lpp-s1.git
```
6. Copy public URL form terminal (by running  cloudflared tunnel  --url http:localhost:9000 command.). then, paste it on env file (named .env.develop_dev) for config in VITE_SERVER_ENDPOINT line.
```
npm run start:dev
cloudflared tunnel --url http:localhost:5181
```
7. Open public URL from both projects on your browser and/or open on LINE OA by clicking on the rich menu.

