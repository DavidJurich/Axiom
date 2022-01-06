package me.cyadd.axiom.client.module.client;

import me.cyadd.axiom.client.Axiom;
import me.cyadd.axiom.client.AxiomInput;
import me.cyadd.axiom.client.enums.EnumModuleType;
import me.cyadd.axiom.client.module.AxiomModule;

public class AxiomModuleHelp extends AxiomModule {

	public AxiomModuleHelp() {
		super("Help", EnumModuleType.CLIENT);
		this.setDescription("Displays the help menu in the client.");
		this.setCustomSyntax(".<module> help");
	}

	@Override
	public void onToggle() {
		String help = "Availiable Commands: ";
		for (final AxiomModule module : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			help += (module.getName().toLowerCase() + ", ");
		}
		AxiomInput.addChat("All §3Axiom§f chat commands are handled with the prefix: '" + AxiomInput.prefix + "'.");
		AxiomInput.addChat(help + "tc.");
	}

	@Override
	public void runCommand(final String cmd) {
		for (final AxiomModule module : Axiom.getAxiom().getModuleManager().getLoadedModules().values()) {
			if (cmd.startsWith(AxiomInput.prefix + "help " + module.getName().toLowerCase())) {
				AxiomInput.addChat(module.getName() + " help: " + module.getDescription());
				AxiomInput.addChat(module.getName() + " Syntax: " + module.getCustomSyntax());
			}
		}
	}

}
