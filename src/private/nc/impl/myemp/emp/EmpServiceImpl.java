package nc.impl.myemp.emp;

import nc.bs.bd.baseservice.md.BatchBaseService;
import nc.itf.myemp.emp.IEmpService;
import nc.vo.myemp.emp.EmpVO;

public class EmpServiceImpl extends BatchBaseService<EmpVO> implements
		IEmpService {
	public EmpServiceImpl() {
		super("a52a9d67-3f43-4c88-a21b-6ec768aa3620");
	}
}
