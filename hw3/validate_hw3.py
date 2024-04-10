#!/bin/python3

import subprocess
import os
import sys
import shutil
import csv

# 測試案例和答案的路徑列表
HW_NAME = "hw3"
TC_DIR = f"./data"
TESTCASES = [
    f"{TC_DIR}/tc0.csv",
    f"{TC_DIR}/tc1.csv",
    f"{TC_DIR}/tc2.csv",
    f"{TC_DIR}/tc3.csv",
    f"{TC_DIR}/tc4.csv"
]

ANS_DIR = TC_DIR
ANSWERS = [
    f"{ANS_DIR}/ans0.csv",
    f"{ANS_DIR}/ans1.csv",
    f"{ANS_DIR}/ans2.csv",
    f"{ANS_DIR}/ans3.csv",
    f"{ANS_DIR}/ans4.csv"
]

PROGRAM_NAME = "HtmlParser"
DIFF_DIR = os.path.join('.', 'diff')
source_code_path = os.path.expanduser(f"./{PROGRAM_NAME}.java")
byte_code_path = os.path.expanduser(f"{PROGRAM_NAME}")
# 檢查是否存在 HtmlParser.java 檔案，並且路徑正確
if not os.path.exists(source_code_path):
    print(f"❌錯誤：{PROGRAM_NAME}.java 文件不存在或路徑錯誤!")
    print(f"請確認 {PROGRAM_NAME}.java 是否存在於 ~/{HW_NAME}/ 目錄下!")
    exit(1)

jsoup_package_path = os.path.expanduser(f"./jsoup.jar")
# 檢查是否存在 Jsoup.jar 檔案，並且路徑正確
if not os.path.exists(jsoup_package_path):
    print(f"❌錯誤：jsoup.jar 文件不存在或路徑錯誤!")
    print(f"請確認 jsoup.jar 是否存在於 ~/{HW_NAME}/ 目錄下!")
    exit(1)

# 編譯 HtmlParser.java
compile_opt = "-cp"
cp_arg = f".:{jsoup_package_path}"
if subprocess.call(["javac", compile_opt, cp_arg, source_code_path]) == 0:
    print("編譯完成")
else:
    print("❌錯誤：編譯時發生錯誤")
    exit(1)

# 執行測試案例
def run_testcase(tc_number, testcase, answer):
    try:
        # 先清空之前產生過的檔案
        output_file_name = "output.csv"
        if os.path.exists(output_file_name):
            subprocess.run(["rm", output_file_name])
        # 讀取測資
        ARGS = []
        with open(testcase, newline='') as csvfile:
            reader = csv.reader(csvfile)
            # 逐行吃參數
            for row in reader:
                ARGS.append(row)
        # 跑程式與比對
        mode=task=stock=start=end=''
        for row in ARGS:
            task = row[1]
            if task == "0":
                mode = row[0]
                subprocess.run(["java", compile_opt, cp_arg, byte_code_path, mode, task], timeout=60)
            else:
                mode, task, stock, start, end = row
                subprocess.run(["java", compile_opt, cp_arg, byte_code_path, mode, task, stock, start, end], timeout=60)
        
        if not os.path.exists(output_file_name):
            print(f"❌找不到 {output_file_name}")

        file_name = os.path.basename(answer)
        diff_process = subprocess.run(["sdiff", "--ignore-trailing-space", "-w", "120", "-l", output_file_name, answer], text=True, capture_output=True)
        if diff_process.returncode == 0:
            print(f"{file_name}: ✅")
        else:
            # Save output of diff to file
            diff_log = os.path.join(f'diff/testcase{tc_number}', answer.split('/')[-1] + "_diff")
            if not os.path.exists(os.path.dirname(diff_log)):
                os.makedirs(os.path.dirname(diff_log))
            with open(diff_log, 'w') as f:
                f.write(diff_process.stdout)
            print(f"{file_name}❌\t| 結果已存至 '{diff_log}'")
            # 先清空之前產生過的檔案
            output_file_name = "output.csv"
            if os.path.exists(output_file_name):
                subprocess.run(["rm", output_file_name])
    except FileNotFoundError as e:
        print("找不到 sdiff 命令")
        print("錯誤訊息：", e.stderr)
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
        for i, (testcase, answer) in enumerate(zip(TESTCASES, ANSWERS)):
            print(f"testcase{i}: \n", end='')
            run_testcase(i, testcase, answer)
    else:
        # 如果傳遞了參數，只執行指定編號的測試案例
        testcase_number = int(sys.argv[1])
        if 0 <= testcase_number < len(TESTCASES):
            print(f"testcase{testcase_number}: ", end='')
            run_testcase(testcase_number, TESTCASES[testcase_number], ANSWERS[testcase_number])
        else:
            print("❌錯誤：無效的測試案例編號")
            exit(1)
