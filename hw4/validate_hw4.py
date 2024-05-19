#!/bin/python3

import subprocess
import os
import sys
import shutil

base_path = "./ans"

# 測試案例和答案的路徑列表
TESTCASES = [
    f"{base_path}/tc0.txt",
    f"{base_path}/tc1.txt",
    f"{base_path}/tc2.txt",
    f"{base_path}/tc3.txt",
    f"{base_path}/tc4.txt"
]

ANSWERS = [
    f"{base_path}/ans0.txt",
    f"{base_path}/ans1.txt",
    f"{base_path}/ans2.txt",
    f"{base_path}/ans3.txt",
    f"{base_path}/ans4.txt"
]

docs_path = f"{base_path}/docs.txt"
docs_path = os.path.expanduser(docs_path)

DIFF_DIR = os.path.join('.', 'diff')
source_code_path = os.path.expanduser("./TFIDFCalculator.java")

# 檢查是否存在 TFIDFCalculator.java 檔案，並且路徑正確
if not os.path.exists(source_code_path):
    print("❌錯誤：TFIDFCalculator.java 文件不存在或路徑錯誤!")
    print("請確認 TFIDFCalculator.java 是否存在於 ~/hw4/ 目錄下!")
    exit(1)

# 編譯 CodeGenerator.java
if subprocess.call(["javac", source_code_path]) == 0:
    print("編譯完成")
else:
    print("❌錯誤：編譯時發生錯誤")
    exit(1)

# 執行測試案例
def run_testcase(tc_number, testcase, answer_file):
    try:
        # 先清空之前產生過的檔案
        output_file_name = "output"
        if os.path.exists(output_file_name):
            subprocess.run(["rm", output_file_name])
        
        testcase = os.path.expanduser(testcase)
        answer_file = os.path.expanduser(answer_file)

        if not os.path.exists(docs_path):
            print(f"❌找不到 {docs_path}")
        if not os.path.exists(testcase):
            print(f"❌找不到 {testcase}")
        
        subprocess.run(["java", source_code_path, docs_path, testcase], timeout=60)
        # subprocess.run(["python3", "TFIDFCalculator.py", docs_path, testcase], timeout=60)
        output_file_name = "output.txt"
        if not os.path.exists(output_file_name):
            print(f"❌找不到 {output_file_name}")

        file_name = os.path.basename(answer_file)
        diff_process = subprocess.run(["sdiff","-w", "120", "-l", output_file_name, answer_file], text=True, capture_output=True)
        if diff_process.returncode == 0:
            print(f"{file_name}: ✅")
        else:
            # Save output of diff to file
            diff_log = os.path.join(f'diff/testcase{tc_number}', answer_file.split('/')[-1] + "_diff")
            if not os.path.exists(os.path.dirname(diff_log)):
                os.makedirs(os.path.dirname(diff_log))
            with open(diff_log, 'w') as f:
                f.write(diff_process.stdout)
            print(f"{file_name}: ❌\t| 結果已存至 '{diff_log}'")

    except FileNotFoundError as e:
        print("找不到 sdiff 命令")
    except subprocess.TimeoutExpired:
        print("❌執行超時")
        sys.exit(1)  # 終止程式


if __name__ == '__main__':
    # Remove files in DIFF_DIR
    if os.path.exists(DIFF_DIR):
        shutil.rmtree(DIFF_DIR)
    os.mkdir(DIFF_DIR)
    
    # 如果沒有傳遞參數，預設執行所有測試案例
    if len(sys.argv) == 1:
        for i, (testcase, answer_file) in enumerate(zip(TESTCASES, ANSWERS)):
            print(f"testcase{i}: \n", end='')
            run_testcase(i, testcase, answer_file)
    else:
        # 如果傳遞了參數，只執行指定編號的測試案例
        testcase_number = int(sys.argv[1])
        if 0 <= testcase_number < len(TESTCASES):
            print(f"testcase{testcase_number}: ", end='')
            run_testcase(testcase_number, TESTCASES[testcase_number], ANSWERS[testcase_number])
        else:
            print("❌錯誤：無效的測試案例編號")
            exit(1)
    
