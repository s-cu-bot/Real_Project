import os
import time
import psutil
from pywinauto import Application, Desktop

#프로세스명 입력하면 포커스해줌
'''
def find_process(program) :
    for process in psutil.process_iter(["pid", "name"]) :
        if process.info["name"] == program :
            return process
'''

#삭제할 프로그램
target_program = "INISAFE CrossWeb EX V3"

try :
    #제어판>프로그램 및 기능 실행
    app = Application().start("control.exe appwiz.cpl")
    time.sleep(1)

    #"프로그램 및 기능" 창 포커스
    desktop = Desktop()
    program_list = desktop['프로그램 및 기능']
    list_view = program_list.child_window(class_name='SysListView32')

    #제어판에서 삭제할 프로그램 검색
    item_count = list_view.item_count()
    for i in range(item_count) :
        item = list_view.get_item(i)
        if target_program == item.text() :
            list_view.select(i)
            item_rect = item.rectangle()
            list_view.double_click(coords=(item_rect.left + 5, item_rect.top + 5))
            break

except Exception as e:
    print(e)

try :
    time.sleep(1)
    #팝업창 실행 및 닫기
    #popup_process = find_process("SetupPKG.exe")
    popup_app = Application().connect(path="Au_.exe")
    popup_app.Dialog.Button0.click()
    time.sleep(5)
    program_list.close()

except Exception as e :
    program_list.close()
    print(e)

os._exit(1)

#해당 구문은 팝업창에서 포커스할 button 찾는 방법
#print(popup_app.Dialog.print_control_identifiers())