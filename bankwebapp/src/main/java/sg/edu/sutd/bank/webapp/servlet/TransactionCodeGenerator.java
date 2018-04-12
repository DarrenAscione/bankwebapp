/*
 * Copyright 2017 SUTD Licensed under the
	Educational Community License, Version 2.0 (the "License"); you may
	not use this file except in compliance with the License. You may
	obtain a copy of the License at

https://opensource.org/licenses/ECL-2.0

	Unless required by applicable law or agreed to in writing,
	software distributed under the License is distributed on an "AS IS"
	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
	or implied. See the License for the specific language governing
	permissions and limitations under the License.
 */

package sg.edu.sutd.bank.webapp.servlet;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;

public class TransactionCodeGenerator {

	public static List<String> generateCodes(int num) {
		List<String> codes = new ArrayList<String>(num);
		for (int idx = 0; idx < num; ++idx) {
			UID code = new UID();
			codes.add(code.toString());
		}
		return codes;
	}
}