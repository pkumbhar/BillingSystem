package com.databaseAdapter;

import com.entity.Branch;
import com.entity.EmployeeRoleMapping;
import com.entity.FinancialYear;

/**
 * Created by Admin on 02-November-2-2017.
 */

public interface BillingDatabaseInterface {
    public FinancialYear findFinancialYearByName(String yearName) throws Exception;
    public Branch findBranchbByName(String branchName)throws Exception;
    public EmployeeRoleMapping findEmployeeRoleMapping(String role)throws Exception;

}
