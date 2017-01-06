package com.redhat.jenkinsci.installinfrared;

def InstallInfraRed() {
    git url: "https://github.com/rhosqeauto/InfraRed.git"
    git url: "https://code.engineering.redhat.com/gerrit/rhos-infrared"
    sh """
        virtualenv $venv
        . $venv/bin/activate
        pip install -U pip
        pip install $WORKSPACE/InfraRed/.
        host=$HOSTNAME

        pushd InfraRed
        pip install . > $WORKSPACE/pip.log
        pip list > $WORKSPACE/pip_packages.txt
        cp ansible.cfg.example ansible.cfg
        mkdir -p "$venv/cp"
        sed -i "s|^control_path.*|control_path = $venv/cp/%%h-%%r|" ansible.cfg
    """
}