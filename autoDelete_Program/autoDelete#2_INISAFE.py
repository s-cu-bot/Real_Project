import os
import time
from pywinauto import Application

#uninstall 파일 위치
target_program = "C:/Program Files (x86)/INITECH/INISAFE Web EX Client/UnINIS_EX.exe"

try :
    #uninstall 프로그램 실행
    Application().start(target_program)
    time.sleep(2)
    app = Application().connect(path="Au_.exe")

    #uninstall 진행
    app.Dialog.Button0.click()
    time.sleep(6)

    #uninstall 끝난 창 닫기
    app.Dialog.Button1.click()

except Exception as e:
    print(e)

os._exit(1)

#해당 구문은 팝업창에서 포커스할 button 찾는 방법
#print(popup_app.Dialog.print_control_identifiers())