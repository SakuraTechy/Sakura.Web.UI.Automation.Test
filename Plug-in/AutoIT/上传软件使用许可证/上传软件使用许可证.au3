;ControlFocus("title","text",controlID) Edit1=Edit instance 1
;control ID��class��instance���
;ʶ��windows����
ControlFocus("��", "","Edit1")

; Wait 10 seconds for the Upload window to appear
;���ڵȴ�ʮ��
WinWait("[CLASS:#32770]","",10)


; Set the File name text on the Edit field
;���������������Ҫ�ϴ��ĵ�ַ
ControlSetText("��", "", "Edit1", "D:\King\Eclipse\Ankki.Dmp.Web.Test\Plug-in\AutoIT\�ϴ����ʹ�����֤\B27CA2A58CC55512_audit.lic")

Sleep(1000)

; Click on the Open button
;���[�򿪡���ť
ControlClick("��", "","Button1");