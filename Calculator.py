import tkinter as tk

def on_click(char):
    if char == "C":
        entry_var.set("")
    elif char == "=":
        try:
            expression = entry_var.get().replace("×", "*").replace("÷", "/")
            result = str(eval(expression))
            entry_var.set(result)
        except Exception:
            entry_var.set("Error")
    else:
        entry_var.set(entry_var.get() + char)

root = tk.Tk()
root.title("Calculator")
root.geometry("320x400")
root.resizable(False, False)


entry_var = tk.StringVar()
entry = tk.Entry(root, textvariable=entry_var, font=("Segoe UI", 20),
                 bd=8, relief="ridge", justify="right")
entry.pack(side="top", fill="both", ipadx=8, ipady=8, padx=8, pady=8)


buttons = [
    ["7", "8", "9", "÷"],
    ["4", "5", "6", "×"],
    ["1", "2", "3", "-"],
    ["0", ".", "C", "+"],
    ["="]  
]

frame = tk.Frame(root)
frame.pack(expand=True, fill="both")


for r, row in enumerate(buttons):
    for c, btn_text in enumerate(row):
        btn = tk.Button(frame, text=btn_text, font=("Segoe UI", 18),
                        command=lambda t=btn_text: on_click(t))
        if btn_text == "=":
            btn.grid(row=r, column=0, columnspan=4, sticky="nsew", padx=2, pady=2)
        else:
            btn.grid(row=r, column=c, sticky="nsew", padx=2, pady=2)


for i in range(5):
    frame.grid_rowconfigure(i, weight=1)
for i in range(4):
    frame.grid_columnconfigure(i, weight=1)

root.mainloop()
