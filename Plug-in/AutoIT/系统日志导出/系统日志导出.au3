;ControlFocus("title","text",controlID) Edit1=Edit instance 1
;control ID��class��instance���
;ʶ��windows����
ControlFocus("���Ϊ", "","Edit1")

; Wait 10 seconds for the Upload window to appear
;���ڵȴ�ʮ��
WinWait("[CLASS:#32770]","",10)


; Set the File name text on the Edit field
;���������������Ҫ�ϴ��ĵ�ַ
ControlSetText("���Ϊ", "", "Edit1", "D:\King\Eclipse\Ankki.DBSG.Web.Test\Plug-in\AutoIT\ϵͳ��־����\ϵͳ��־����.xlsx")

Sleep(1000)

; Click on the Open button
;���[�򿪡���ť
ControlClick("���Ϊ", "","Button2");