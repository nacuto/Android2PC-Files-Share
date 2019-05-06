using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Configuration;
using System.Windows.Forms;

namespace AndroidDisk
{
    public partial class Form1 : Form
    {
        public Process proc;
        public string dirPath = System.Windows.Forms.Application.StartupPath;

        public Form1()
        {
            InitializeComponent();

            IPTB.Text = ConfigurationManager.AppSettings["ip"];
            portTB.Text = ConfigurationManager.AppSettings["port"];
            userTB.Text = ConfigurationManager.AppSettings["user"];
            passTB.Text = ConfigurationManager.AppSettings["pass"];
            anonymousCB.Checked =Convert.ToBoolean(ConfigurationManager.AppSettings["anonymous"]);

            this.notifyIcon1.Visible = false;
        }
        
        private void stop_Click(object sender, EventArgs e)
        {  
            if(proc!=null) proc.Kill();
            delFile();

            unlockUI();
            if (!startBtn.Enabled)
            {
                startBtn.Enabled = true;
                stopBtn.Enabled = false;
            }
            
        }

        private void delFile()
        {
            Process delFile = new Process();
            string fileName = "delFile.bat";
            delFile.StartInfo.WorkingDirectory = dirPath;
            delFile.StartInfo.FileName = fileName;
            delFile.StartInfo.CreateNoWindow = true;
            delFile.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
            delFile.Start();
        }

        private void start_Click(object sender, EventArgs e)
        {
            string ip = IPTB.Text, port = portTB.Text;
            string user = "", pass = "";
            if (!anonymousCB.Checked)
            {
                user = userTB.Text;
                pass = passTB.Text;
            }
            
            proc = new Process();
            string fileName = "CJT.bat";
            proc.StartInfo.WorkingDirectory = dirPath;
            proc.StartInfo.FileName = fileName;
            //proc.StartInfo.CreateNoWindow = true;
            //proc.StartInfo.WindowStyle = ProcessWindowStyle.Hidden;
            proc.StartInfo.Arguments = ip+" "+port + " " + user + " " + pass;
            Console.WriteLine(ip + " " + port + " " + user + " " + pass);
            proc.Start();

            lockUI();
            startBtn.Enabled = false;
            stopBtn.Enabled = true;
            saveConfig();
        }

        private void saveConfig()
        {
            string configFile = System.Windows.Forms.Application.ExecutablePath;
            Configuration config = ConfigurationManager.OpenExeConfiguration(configFile);

            config.AppSettings.Settings["ip"].Value = IPTB.Text;
            config.AppSettings.Settings["port"].Value = portTB.Text;
            config.AppSettings.Settings["user"].Value = userTB.Text;
            config.AppSettings.Settings["pass"].Value = passTB.Text;
            if(anonymousCB.Checked) config.AppSettings.Settings["anonymous"].Value = "true";
            else config.AppSettings.Settings["anonymous"].Value = "false";

            config.Save(ConfigurationSaveMode.Modified);
            ConfigurationManager.RefreshSection("appSettings");
        }

        private void lockUI()
        {
            IPTB.Enabled = false;
            portTB.Enabled = false;
            anonymousCB.Enabled = false;
            userTB.Enabled = false;
            passTB.Enabled = false;
            showPassCB.Enabled = false;
        }

        private void unlockUI()
        {
            IPTB.Enabled = true;
            portTB.Enabled = true;
            anonymousCB.Enabled = true;
            userTB.Enabled = true;
            passTB.Enabled = true;
            showPassCB.Enabled = true;
        }

        private void showPassCB_CheckedChanged(object sender, EventArgs e)
        {
            if (showPassCB.Checked)
            {
                passTB.PasswordChar = new char();
            }
            else
            {
                passTB.PasswordChar = '*';
            }
        }
        
        private void anonymousCB_CheckedChanged(object sender, EventArgs e)
        {
            anonymousCheck(anonymousCB.Checked);
        }
        private void anonymousCheck(bool isChecked)
        {
            if (isChecked)
            {
                userTB.Enabled = false;
                passTB.Enabled = false;
                showPassCB.Enabled = false;
            }
            else
            {
                userTB.Enabled = true;
                passTB.Enabled = true;
                showPassCB.Enabled = true;
            }
        }

        private void Form1_Deactivate(object sender, EventArgs e)
        {
            //当窗体为最小化状态时
            if (this.WindowState == FormWindowState.Minimized)
            {
                this.notifyIcon1.Visible = true; //显示托盘图标
                this.Hide();//隐藏窗体
                this.ShowInTaskbar = false;//图标不显示在任务栏
            }
        }

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            //当用户点击窗体右上角X按钮或(Alt + F4)时 发生
            if (e.CloseReason == CloseReason.UserClosing)          
            {
                e.Cancel = true;
                this.ShowInTaskbar = false; //图标不显示在任务栏
                //this.notifyIcon1.Icon = this.Icon;
                this.Hide();
            }
        }

        private void notifyIcon1_MouseClick(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                contextMenuStrip1.Show();
            }

            if (e.Button == MouseButtons.Left)
            {
                this.Visible = true;
                this.WindowState = FormWindowState.Normal;
            }
        }

        private void 退出ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if(proc!=null) proc.Kill();
            delFile();
            Application.Exit();
        }
    }
}
