/*================================================================================
Copyright (c) 2008 VMware, Inc. All Rights Reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

* Neither the name of VMware, Inc. nor the names of its contributors may be used
to endorse or promote products derived from this software without specific prior 
written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
IN NO EVENT SHALL VMWARE, INC. OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
POSSIBILITY OF SUCH DAMAGE.
================================================================================*/

package com.vmware.vim25.mo.samples.vm;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.Datacenter;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;
import com.vmware.vim25.mo.util.CommandLineParser;
import com.vmware.vim25.mo.util.OptionSpec;


/**
*<pre>
*This sample implements the function as the one in VI SDK sample package, including:
*<ul>
*<li> Locates a pre-existing virtual machine on the VirtualCenter server.
*<li> Makes a template from this virtual machine for future use.
*<li> Deploys n instances of this template onto a datacenter.
*</ul>
*<b>Parameters:</b>
*DatacenterName  [required] : DatacenterName
*vmPath          [required] : A path to the VM inventory
*CloneName       [required] : Name of the Clone
*
*
*<b>Command: To clone a virtual machine</b>
*run.bat com.vmware.samples.vm.VMClone --url <webserviceurl> 
*--username <username> --password <password> --DatacenterName <DatacenterName> 
*--vmPath <vmPath>  --CloneName <CloneName>
*
*Example:
*--url https://10.17.218.228/sdk --username administrator --password mypass --DatacenterName Datacenter 
*--vmPath Datacenter/vm/W2K3St --CloneName w2k3Std_cloneTest
*</pre>
* @author sjin
* This is a sample converted from VI SDK samples.
*/


public class VMClone 
{
   public static void main(String[] args) throws Exception
   {
	   CommandLineParser clp = new CommandLineParser(constructOptions(), args);
	   
	   String urlStr = clp.get_option("https://130.65.132.200/sdk");
	   String username = clp.get_option("administrator");
	   String password = clp.get_option("12!@qwQW");
	   String cloneName = clp.get_option("CloneDhinesh");
	   String vmPath = clp.get_option("Team08_DC/Dhinesh_Pool/T08-vm01-Ubuntu32-Dhinesh");
	   String datacenterName= clp.get_option("Team08_DC");

	   try
	   {
		   ServiceInstance si = new ServiceInstance(new URL(urlStr), username, password, true);
		   VirtualMachine vm = (VirtualMachine) si.getSearchIndex().findByInventoryPath(vmPath);
		   Datacenter dc = (Datacenter) si.getSearchIndex().findByInventoryPath(datacenterName);
		   
		   if(vm==null || dc ==null)
		   {
			   System.out.println("VirtualMachine or Datacenter path is NOT correct. Pls double check. ");
			   return;
		   }
		   Folder vmFolder = dc.getVmFolder();
	
		   VirtualMachineCloneSpec cloneSpec = new VirtualMachineCloneSpec();
		   cloneSpec.setLocation(new VirtualMachineRelocateSpec());
		   cloneSpec.setPowerOn(false);
		   cloneSpec.setTemplate(false);

		   Task task = vm.cloneVM_Task(vmFolder, cloneName, cloneSpec);
		   System.out.println("Launching the VM clone task. It might take a while. Please wait for the result ...");
		   
		   String status = 	task.waitForMe();
		   if(status==Task.SUCCESS)
		   {
	            System.out.println("Virtual Machine got cloned successfully.");
		   }
		   else
		   {
			   System.out.println("Failure -: Virtual Machine cannot be cloned");
		   }
	   }
	   catch(RemoteException re)
	   {
		   re.printStackTrace();
	   }
	   catch(MalformedURLException mue)
	   {
		   mue.printStackTrace();
	   }
	}

   private static OptionSpec[] constructOptions() 
   {
      OptionSpec [] useroptions = new OptionSpec[3];
      useroptions[0] = new OptionSpec("DatacenterName","String",1
              ,"Name of the Datacenter", null);
      useroptions[1] = new OptionSpec("vmPath","String",1,
    		  "Path to the VM inventory", null);
      useroptions[2] = new OptionSpec("CloneName","String",1,
              "Name of the Clone", null);
      return useroptions;
   }

}
