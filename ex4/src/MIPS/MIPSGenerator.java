/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class MIPSGenerator
{
	private int WORD_SIZE=4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile()
	{
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}
	public void print_int()
	{
		fileWriter.format("\tlw $a0,0($sp)\n");
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $a0,32\n");
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");
	}
	public void print_string()
	{
		fileWriter.format("\tli $v0,4\n");
		fileWriter.format("\tlw $a0,0($sp)\n");
		fileWriter.format("\tsyscall\n");
// 		// next 3 lines print a space
// 		fileWriter.format("\tli $a0,32\n");
// 		fileWriter.format("\tli $v0,11\n");
// 		fileWriter.format("\tsyscall\n");

	}
	//public TEMP addressLocalVar(int serialLocalVarNum)
	//{
	//	TEMP t  = TEMP_FACTORY.getInstance().getFreshTEMP();
	//	int idx = t.getSerialNumber();
	//
	//	fileWriter.format("\taddi Temp_%d,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
	//	
	//	return t;
	//}
	public void allocate_word(String var_name, int value)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .word %d\n",var_name, value);
		fileWriter.format(".text\n");
	}
	public void allocate_string(String var_name, String value)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\t%s: .asciiz %s\n",var_name, value);
		fileWriter.format(".text\n");
	}

	public void load(TEMP dst,String address)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,%s\n",idxdst,address);
	}
	public void load(String dst, TEMP address, int offset)
	{
		int idx=address.getSerialNumber();
		fileWriter.format("\tlw %s,%d(Temp_%d)\n", dst, offset, idx);
	}
	public void load(String dst, String address, int offset)
	{
		fileWriter.format("\tlw %s,%d(%s)\n", dst, offset, address);
	}
	public void load(TEMP dst, TEMP src)
	{
		int idxsrc=src.getSerialNumber();
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,0(Temp_%d)\n", idxdst, idxsrc);
	}
	public void li(TEMP t,int value)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tli Temp_%d,%d\n",idx,value);
	}
	public void li(String t,int value)
	{
		fileWriter.format("\tli %s,%d\n",t,value);
	}
	public void la(TEMP t,String value)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tla Temp_%d,%s\n",idx,value);
	}
	public void la(String dst,String value)
	{
		fileWriter.format("\tla %s,%s\n",dst,value);
	}
	public void store(String address,TEMP src)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,%s\n",idxsrc,address);		
	}
	public void store(TEMP dst,TEMP src)
	{
		int idxsrc=src.getSerialNumber();
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,0(Temp_%d)\n",idxsrc,idxdst);		
	}
	public void store(TEMP dst,String src, int offset)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tsw %s,%d(Temp_%d)\n", src, offset, idxdst);		
	}
	
	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}
	public void add(String dst,String oprnd1,int oprnd2)
	{
		fileWriter.format("\tadd %s,%s,%d\n",dst, oprnd1, oprnd2);
	}
	public void add(TEMP dst,TEMP oprnd1,int oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tadd Temp_%d,Temp_%d,%d\n",dstidx,i1,oprnd2);
	}
	public void addu(String s1, String s2, int i)
	{
		fileWriter.format("\taddu %s,%s,%d\n", s1, s2, i);				
	}
	public void addu(TEMP dst, TEMP op1, TEMP op2)
	{
		int i1 =op1.getSerialNumber();
		int i2 =op2.getSerialNumber();
		int dstidx=dst.getSerialNumber();
		fileWriter.format("\taddu Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);				
	}
	public void sub(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tsub Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}
	public void subu(String dst, String op1, int op2)
	{
		fileWriter.format("\tsubu %s,%s,%d\n",dst, op1, op2);	
	}
	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tmul Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}
	public void mul(TEMP dst,TEMP oprnd1,int oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tmul Temp_%d,Temp_%d,%d\n",dstidx,i1,oprnd2);
	}
	public void mul(String dst,String oprnd1,int oprnd2)
	{
		fileWriter.format("\tmul %s,%s,%d\n",dst, oprnd1, oprnd2);
	}
	public void div(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tdiv Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}
	public void label(String inlabel)
	{
		if (inlabel.equals("main"))
		{
			fileWriter.format("%s:\n","user_main");
		}
		else
		{
			fileWriter.format("%s:\n",inlabel);
		}
	}
	public void label_main()
	{
		fileWriter.format(".text\n");
		fileWriter.format("%s:\n","main");
	}
	public void jump(String inlabel)
	{
		fileWriter.format("\tj %s\n",inlabel);
	}
	public void jal(String inlabel)
	{
		fileWriter.format("\tjal %s\n",inlabel);
	}
	public void jalr(String dst)
	{
		fileWriter.format("\tjalr %s\n",dst);
	}
	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tblt Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void bltz(TEMP oprnd1,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		
		fileWriter.format("\tbltz Temp_%d,%s\n",i1,label);				
	}
	public void bgt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbgt Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbge Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void bge(TEMP oprnd1,int imm,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		fileWriter.format("\tli $s0,%d\n",imm);
		fileWriter.format("\tbge Temp_%d,$s0,%s\n",i1,label);				
	}
	public void bge(TEMP oprnd1,String oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		fileWriter.format("\tbge Temp_%d,%s,%s\n",i1,oprnd2,label);				
	}
	public void ble(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tble Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void ble(TEMP oprnd1,int imm,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		fileWriter.format("\tli $s0,%d\n",imm);
		fileWriter.format("\tble Temp_%d,$s0,%s\n",i1,label);				
	}
	public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbne Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void bne(String s1, String s2, String label)
	{
		fileWriter.format("\tbne %s,%s,%s\n", s1, s2, label);				
	}
	
	public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		
		fileWriter.format("\tbeq Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}
	public void beq(String s1, int i, String label)
	{
		fileWriter.format("\tbeq %s,%d,%s\n", s1, i, label);				
	}
	
	public void beqz(TEMP oprnd1,String label)
	{
		int i1 =oprnd1.getSerialNumber();
				
		fileWriter.format("\tbeq Temp_%d,$zero,%s\n",i1,label);				
	}
	public void bnez(TEMP oprnd1,String label)
	{
		int i1 =oprnd1.getSerialNumber();
				
		fileWriter.format("\tbne Temp_%d,$zero,%s\n",i1,label);				
	}
	
	public void mov(TEMP oprnd1, TEMP oprnd2)
	{
		int i1 =oprnd1.getSerialNumber();
		int i2 =oprnd2.getSerialNumber();
		fileWriter.format("\tmove Temp_%d,Temp_%d\n",i1, i2);				
	}
	
	public void mov(String s, TEMP oprnd1)
	{
		int i1 =oprnd1.getSerialNumber();
		fileWriter.format("\tmove %s,Temp_%d\n", s, i1);				
	}
	public void mov(TEMP dst, String src)
	{
		int dst_ind =dst.getSerialNumber();
		fileWriter.format("\tmove Temp_%d,%s\n", dst_ind, src);				
	}
	
	public void lb(String s1, int i, String s2)
	{
		fileWriter.format("\tlb %s,%d(%s)\n", s1, i, s2);				
	}
	
	public void funcPrologue()
	{
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $ra,0($sp)\n");
		fileWriter.format("\tsubu $sp,$sp,4\n");
		fileWriter.format("\tsw $fp,0($sp)\n");
		fileWriter.format("\tmove $fp,$sp\n");
		
		for (int i = 0; i < 10; i++){
			fileWriter.format("\tsubu $sp,$sp,4\n");
			fileWriter.format("\tsw $t%d,0($sp)\n", i);
		}			
	}
	
	public void funcEpilogue()
	{
		fileWriter.format("\tmove $sp,$fp\n");
		for (int i = 0; i < 10; i++){
			fileWriter.format("\tlw $t%d,-%d($sp)\n", i,(i+1)*4);
		}
		fileWriter.format("\tlw $fp,0($sp)\n");
		fileWriter.format("\tlw $ra,4($sp)\n");
		fileWriter.format("\taddu $sp,$sp,8\n");
		fileWriter.format("\tjr $ra\n");
	}
	
	public void addStrings(TEMP dst,TEMP t1,TEMP t2, String str1_len_loop, String str1_len_end, String str2_len_loop, String str2_len_end, String copy_str1_loop,
			 	String copy_str1_end, String copy_str2_loop, String copy_str2_end){
		
		int dst_ind = dst.getSerialNumber();
		int t1_ind = t1.getSerialNumber();
		int t2_ind = t2.getSerialNumber();
		
		fileWriter.format("\tli $s0,1\n");
		fileWriter.format("\tmove $s1,Temp_%d\n", t1_ind);
		fileWriter.format("\t%s:\n", str1_len_loop);
		fileWriter.format("\tlb $s2, 0($s1)\n");
		fileWriter.format("\tbeq $s2, 0, %s\n", str1_len_end);
		fileWriter.format("\taddu $s0, $s0, 1\n");
		fileWriter.format("\taddu $s1, $s1, 1\n");
		fileWriter.format("\tj%s\n", str1_len_loop);
		fileWriter.format("\t%s:\n", str1_len_end);
		
		fileWriter.format("\tmove $s1,Temp_%d\n", t2_ind);
		fileWriter.format("\t%s:\n", str2_len_loop);
		fileWriter.format("\tlb $s2, 0($s1)\n");
		fileWriter.format("\tbeq $s2, 0, %s\n", str2_len_end);
		fileWriter.format("\taddu $s0, $s0, 1\n");
		fileWriter.format("\taddu $s1, $s1, 1\n");
		fileWriter.format("\tj%s\n", str2_len_loop);
		fileWriter.format("\t%s:\n", str2_len_end);
		//$s0 holds the concated string length, including null terminator
		
		fileWriter.format("\tli $v0, 9\n");
		fileWriter.format("\tmove $a0, $s0\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tmove $s0,$v0\n");
		fileWriter.format("\tmove Temp_%d,$v0\n", dst_ind);
		//$s0 holds the address for the new string
		
		fileWriter.format("\tmove $s1,Temp_%d\n", t1_ind);
		fileWriter.format("\t%s:\n", copy_str1_loop);
		fileWriter.format("\tlb $s2, 0($s1)\n");
		fileWriter.format("\tbeq $s2, 0, %s\n", copy_str1_end);
		fileWriter.format("\tsb $s2, 0($s0)\n");
		fileWriter.format("\taddu $s0, $s0, 1\n");
		fileWriter.format("\taddu $s1, $s1, 1\n");
		fileWriter.format("\tj%s\n", copy_str1_loop);
		fileWriter.format("\t%s:\n", copy_str1_end);
		
		
		fileWriter.format("\tmove $s1,Temp_%d\n", t2_ind);
		fileWriter.format("\t%s:\n", copy_str2_loop);
		fileWriter.format("\tlb $s2, 0($s1)\n");
		fileWriter.format("\tbeq $s2, 0, %s\n", copy_str2_end);
		fileWriter.format("\tsb $s2, 0($s0)\n");
		fileWriter.format("\taddu $s0, $s0, 1\n");
		fileWriter.format("\taddu $s1, $s1, 1\n");
		fileWriter.format("\tj%s\n", copy_str2_loop);
		fileWriter.format("\t%s:\n", copy_str2_end);
		fileWriter.format("\taddu $s0, $s0, 1\n");
		fileWriter.format("\tsb $s2, 0($s0)\n");
	}
	
	public void syscall()
	{
		fileWriter.format("\tsyscall\n");
	}
	
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static MIPSGenerator instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected MIPSGenerator() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static MIPSGenerator getInstance()
	{
		if (instance == null)
		{
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new MIPSGenerator();

			try
			{
				/*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
				/*********************************************************************************/
				String dirname="./output/";
				String filename=String.format("MIPS.txt");

				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				instance.fileWriter = new PrintWriter(dirname+filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			/*****************************************************/
			/* [3] Print data section with error message strings */
			/*****************************************************/
			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
			instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
		}
		return instance;
	}
}
